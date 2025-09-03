package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.AddToCartDTO;
import com.lunifer.jo.fpshoppingcart.dto.ApiResponse;
import com.lunifer.jo.fpshoppingcart.dto.CartDTO;
import com.lunifer.jo.fpshoppingcart.dto.UpdateCartItemDTO;
import com.lunifer.jo.fpshoppingcart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartDTO>> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCartByUserId(userId)));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<ApiResponse<CartDTO>> addItemToCart(@PathVariable Long userId, @Valid @RequestBody AddToCartDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Item added", cartService.addItemToCart(userId, dto)));
    }

    @PutMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse<CartDTO>> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Cart item updated", cartService.updateCartItem(userId, cartItemId, dto)));
    }

    @DeleteMapping("/{userId}/item/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.ok(ApiResponse.success("Cart item removed", null));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}