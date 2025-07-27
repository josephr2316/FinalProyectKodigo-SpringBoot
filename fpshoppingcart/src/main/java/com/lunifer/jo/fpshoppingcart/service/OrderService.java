package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateOrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO getOrderById(Long id);
    PagedResponse<OrderDTO> getAllOrders(Pageable pageable);
    OrderDTO createOrder(CreateOrderDTO dto);
    OrderDTO updateOrderStatus(Long id, String newStatus);
    void cancelOrder(Long id);
}
