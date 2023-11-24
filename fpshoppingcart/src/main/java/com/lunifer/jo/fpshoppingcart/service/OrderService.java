package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrderById(Long orderId);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
    void deleteOrder(Long orderId);
    List<OrderDTO> getAllOrders();
}
