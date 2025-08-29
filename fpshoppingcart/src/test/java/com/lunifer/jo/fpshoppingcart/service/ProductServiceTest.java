package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.CreateProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;

import com.lunifer.jo.fpshoppingcart.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
        category.setDescription("Electronic products");
        category.setActive(true);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(10);
        product.setActive(true);
        product.setCategory(category);

        productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Laptop");
        productDTO.setDescription("Gaming laptop");
        productDTO.setPrice(new BigDecimal("999.99"));
        productDTO.setStock(10);
        productDTO.setActive(true);
    }

    @Test
    void shouldGetAllProducts() {
        // Given
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        // When
        PagedResponse<ProductDTO> result = productService.getAllProducts(PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getProductName()).isEqualTo("Laptop");
        verify(productRepository).findAll(any(PageRequest.class));
        verify(productMapper).toProductDTO(product);
    }

    @Test
    void shouldGetProductById() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.getProductById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1L);
        assertThat(result.getProductName()).isEqualTo("Laptop");
        verify(productRepository).findById(1L);
        verify(productMapper).toProductDTO(product);
    }

    @Test
    void shouldCreateProduct() {
        // Given
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setProductName("New Product");
        createProductDTO.setDescription("New description");
        createProductDTO.setPrice(new BigDecimal("199.99"));
        createProductDTO.setStock(5);
        createProductDTO.setCategoryId(1L);

        when(productMapper.toProduct(createProductDTO)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.createProduct(createProductDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo("Laptop");
        verify(productMapper).toProduct(createProductDTO);
        verify(productRepository).save(any(Product.class));
        verify(productMapper).toProductDTO(product);
    }
}
