package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.CartDTO;
import com.lunifer.jo.fpshoppingcart.dto.AddToCartDTO;
import com.lunifer.jo.fpshoppingcart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void shouldGetCartByUserId() throws Exception {
        // Given
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1L);
        cartDTO.setUserId(1L);

        when(cartService.getCartByUserId(1L)).thenReturn(cartDTO);

        // When & Then
        mockMvc.perform(get("/api/carts/user/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cart retrieved successfully"))
                .andExpect(jsonPath("$.data.cartId").value(1L));
    }

    @Test
    @WithMockUser
    void shouldAddItemToCart() throws Exception {
        // Given
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setProductId(1L);
        addToCartDTO.setQuantity(2);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1L);
        cartDTO.setUserId(1L);

        when(cartService.addItemToCart(eq(1L), any(AddToCartDTO.class))).thenReturn(cartDTO);

        // When & Then
        mockMvc.perform(post("/api/carts/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addToCartDTO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Item added to cart successfully"))
                .andExpect(jsonPath("$.data.cartId").value(1L));
    }
}
