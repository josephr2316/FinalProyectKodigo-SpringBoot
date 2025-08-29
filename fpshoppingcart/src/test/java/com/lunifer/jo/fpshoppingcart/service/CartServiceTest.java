package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CartDTO;
import com.lunifer.jo.fpshoppingcart.dto.AddToCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.Cart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import com.lunifer.jo.fpshoppingcart.mapper.CartMapper;
import com.lunifer.jo.fpshoppingcart.repository.CartRepository;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Cart cart;
    private CartDTO cartDTO;
    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
        category.setActive(true);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(10);
        product.setActive(true);
        product.setCategory(category);

        cart = new Cart();
        cart.setCartId(1L);
        cart.setUser(user);

        cartDTO = new CartDTO();
        cartDTO.setCartId(1L);
        cartDTO.setUserId(1L);
    }

    @Test
    void shouldGetCartByUserId() {
        // Given
        when(cartRepository.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(cartMapper.toCartDTO(cart)).thenReturn(cartDTO);

        // When
        CartDTO result = cartService.getCartByUserId(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCartId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        verify(cartRepository).findByUser_UserId(1L);
        verify(cartMapper).toCartDTO(cart);
    }

    @Test
    void shouldAddItemToCart() {
        // Given
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setProductId(1L);
        addToCartDTO.setQuantity(2);

        when(cartRepository.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toCartDTO(cart)).thenReturn(cartDTO);

        // When
        CartDTO result = cartService.addItemToCart(1L, addToCartDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCartId()).isEqualTo(1L);
        verify(cartRepository).findByUser_UserId(1L);
        verify(productRepository).findById(1L);
        verify(cartRepository).save(any(Cart.class));
        verify(cartMapper).toCartDTO(cart);
    }
}
