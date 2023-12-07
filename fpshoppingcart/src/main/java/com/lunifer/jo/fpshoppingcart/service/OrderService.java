package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDTO getOrderById(Long orderId);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);

    void deleteOrder(Long orderId);

    OrderResponse getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir);

    Set<OrderDTO> getAllOrdersByUser(User user);
    Set<OrderDTO> getAllOrdersByUserId(Long userId);
    String cancelOrder(Long orderId);}
