package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<OrderDTO>>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(pageable)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody CreateOrderDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", orderService.createOrder(dto)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @PathVariable Long id, @RequestParam String newStatus) {
        return ResponseEntity.ok(ApiResponse.success("Order status updated", orderService.updateOrderStatus(id, newStatus)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", null));
    }
}
