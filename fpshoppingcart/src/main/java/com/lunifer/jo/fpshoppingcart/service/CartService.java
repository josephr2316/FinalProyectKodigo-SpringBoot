package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addItemToCart(Long userId, AddToCartDTO dto);
    CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO dto);
    void removeItemFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
}
