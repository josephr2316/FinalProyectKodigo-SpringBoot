package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::orderEntityToOrderDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public PagedResponse<OrderDTO> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return PagedResponse.of(orders.map(orderMapper::orderEntityToOrderDTO));
    }

    @Override
    public OrderDTO createOrder(CreateOrderDTO dto) {
        Order order = orderMapper.createOrderDTOToOrder(dto);
        // Aquí puedes agregar lógica de cálculo, validaciones, etc.
        return orderMapper.orderEntityToOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.valueOf(newStatus));
        return orderMapper.orderEntityToOrderDTO(orderRepository.save(order));
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}