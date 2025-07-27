package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import com.lunifer.jo.fpshoppingcart.mapper.ShoppingCartMapper;
import com.lunifer.jo.fpshoppingcart.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user " + userId));
        return cartMapper.cartEntityToCartDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long userId, AddToCartDTO dto) {
        // Tu lógica aquí...
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO dto) {
        // Tu lógica aquí...
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeItemFromCart(Long userId, Long cartItemId) {
        // Tu lógica aquí...
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void clearCart(Long userId) {
        // Tu lógica aquí...
        throw new UnsupportedOperationException("Not implemented yet");
    }
}