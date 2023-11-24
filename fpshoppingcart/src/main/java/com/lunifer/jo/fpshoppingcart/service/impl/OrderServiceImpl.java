package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return OrderMapper.INSTANCE.orderEntityToOrderDTO(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order newOrder = OrderMapper.INSTANCE.orderDTOToOrderEntity(orderDTO);
        Order savedOrder = orderRepository.save(newOrder);
        return OrderMapper.INSTANCE.orderEntityToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);

        if (existingOrder != null) {
            // Convert the list of ProductDTO to Product using OrderMapper
            List<Product> productList = orderDTO.getProductList().stream()
                    .map(productDTO -> OrderMapper.INSTANCE.mapToProductEntity(productDTO))
                    .collect(Collectors.toList());

            // Update products
            existingOrder.setProductList(productList);

            // Update order date
            existingOrder.setOrderDate(orderDTO.getOrderDate());

            // Update status
            existingOrder.setStatus(orderDTO.getStatus());

            Order updatedOrder = orderRepository.save(existingOrder);
            return OrderMapper.INSTANCE.orderEntityToOrderDTO(updatedOrder);
        } else {
            return null;
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper.INSTANCE::orderEntityToOrderDTO)
                .collect(Collectors.toList());
    }
}
