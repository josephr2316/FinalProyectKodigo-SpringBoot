package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import com.lunifer.jo.fpshoppingcart.service.ShoppingCartService;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<OrderDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // Métodos adicionales:
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id, @RequestParam String newStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, newStatus));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}
