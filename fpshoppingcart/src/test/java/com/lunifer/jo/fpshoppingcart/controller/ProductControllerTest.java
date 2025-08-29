package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser
    void shouldGetAllProducts() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("99.99"));
        productDTO.setStock(10);
        productDTO.setActive(true);

        PagedResponse<ProductDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(Collections.singletonList(productDTO));
        pagedResponse.setPageNumber(0);
        pagedResponse.setPageSize(10);
        pagedResponse.setTotalElements(1L);
        pagedResponse.setTotalPages(1);

        when(productService.getAllProducts(any(PageRequest.class))).thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/products")
                .param("page", "0")
                .param("size", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Products retrieved successfully"))
                .andExpect(jsonPath("$.data.content[0].productName").value("Test Product"))
                .andExpect(jsonPath("$.data.content[0].price").value(99.99));
    }

    @Test
    @WithMockUser
    void shouldGetProductById() throws Exception {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("99.99"));
        productDTO.setStock(10);
        productDTO.setActive(true);

        when(productService.getProductById(1L)).thenReturn(productDTO);

        // When & Then
        mockMvc.perform(get("/api/products/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Product found"))
                .andExpect(jsonPath("$.data.productName").value("Test Product"))
                .andExpect(jsonPath("$.data.price").value(99.99));
    }
}