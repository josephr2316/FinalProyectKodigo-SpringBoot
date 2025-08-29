package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.*;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.*;
import com.lunifer.jo.fpshoppingcart.repository.CartRepository;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toCartDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long userId, AddToCartDTO dto) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", dto.getProductId()));

        // Check if product has enough stock
        if (!product.hasStock(dto.getQuantity())) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
        }

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.findItemByProduct(product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + dto.getQuantity();
            if (!product.hasStock(newQuantity)) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
            }
            item.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(dto.getQuantity());
            cart.addItem(newItem);
        }

        cart.calculateTotalPrice();
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartDTO(savedCart);
    }

    @Override
    public CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemDTO dto) {
        Cart cart = getOrCreateCart(userId);
        
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartItemId));

        // Validate stock
        if (!cartItem.getProduct().hasStock(dto.getQuantity())) {
            throw new IllegalArgumentException("Insufficient stock for product: " + cartItem.getProduct().getProductName());
        }

        cartItem.setQuantity(dto.getQuantity());
        cart.calculateTotalPrice();
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartDTO(savedCart);
    }

    @Override
    public void removeItemFromCart(Long userId, Long cartItemId) {
        Cart cart = getOrCreateCart(userId);
        
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartItemId));

        cart.removeItem(cartItem);
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.clearCart();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        Optional<Cart> existingCart = cartRepository.findByUser_UserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        // Create new cart if doesn't exist
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
}