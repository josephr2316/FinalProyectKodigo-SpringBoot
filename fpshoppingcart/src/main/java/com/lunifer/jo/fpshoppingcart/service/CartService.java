package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.AddToCartDTO;
import com.lunifer.jo.fpshoppingcart.dto.CartDTO;
import com.lunifer.jo.fpshoppingcart.dto.UpdateCartItemDTO;

import java.util.List;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addItemToCart(Long userId, AddToCartDTO dto);
    CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO dto);
    void removeItemFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
}
