package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Override
    public PagedResponse<OrderDTO> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return PagedResponse.of(orders.map(orderMapper::toOrderDTO));
    }

    @Override
    public OrderDTO createOrder(CreateOrderDTO dto) {
        Order order = orderMapper.toOrder(dto);
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}