package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDTO getOrderById(Long id);
    PagedResponse<OrderDTO> getAllOrders(Pageable pageable);
    OrderDTO createOrder(CreateOrderDTO dto);

    // Métodos adicionales recomendados:
    OrderDTO updateOrderStatus(Long id, String newStatus);
    void cancelOrder(Long id);
}
