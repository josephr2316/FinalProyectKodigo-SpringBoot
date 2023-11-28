package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Test getAllProducts method")
    @Test
    public void testGetAllProducts() {
        List<Product> products = Collections.singletonList(new Product());
        List<ProductDTO> expectedDTOs = Collections.singletonList(new ProductDTO());

        // Set up mocks
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productEntityToProductDTO(any(Product.class))).thenReturn(new ProductDTO());

        // Call the method and verify the result
        List<ProductDTO> result = productService.getAllProducts();

        // Verify that methods are called correctly
        verify(productRepository).findAll();
        verify(productMapper, times(products.size())).productEntityToProductDTO(any(Product.class));

        // Verify the result
        assertThat(result).isEqualTo(expectedDTOs);
    }

    @DisplayName("Test getProductById method")
    @Test
    public void testGetProductById() {
        long productId = 1L;
        Product product = new Product();
        ProductDTO expectedDTO = new ProductDTO();

        // Set up mocks
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.productEntityToProductDTO(product)).thenReturn(expectedDTO);

        // Call the method and verify the result
        ProductDTO result = productService.getProductById(productId);

        // Verify that methods are called correctly
        verify(productRepository).findById(productId);
        verify(productMapper).productEntityToProductDTO(product);

        // Verify the result
        assertThat(result).isEqualTo(expectedDTO);
    }

    @DisplayName("Test updateProduct method")
    @Test
    public void testUpdateProduct() {
        long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Updated Product");
        Product existingProduct = new Product();
        existingProduct.setProductName("Old Product");
        Product updatedProduct = new Product();

        // Set up mocks
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.productEntityToProductDTO(updatedProduct)).thenReturn(productDTO);

        // Call the method and verify the result
        ProductDTO result = productService.updateProduct(productDTO, productId);

        // Verify that methods are called correctly
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
        verify(productMapper).productEntityToProductDTO(updatedProduct);

        // Verify the result
        assertThat(result).isEqualTo(productDTO);
    }

    @DisplayName("Test saveProduct method")
    @Test
    public void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("TestProductDTO");
        productDTO.setPrice(new BigDecimal("19.99"));
        CategoryDTO categoryDTOTest = new CategoryDTO();
        categoryDTOTest.setCategoryName("CategoryTest");
        productDTO.setCategoryDTO(categoryDTOTest);
        Product productEntityToSave = new Product();
        Product savedProductEntity = new Product();

        // Set up mocks
        when(productMapper.productDTOToProductEntity(productDTO)).thenReturn(productEntityToSave);
        when(productRepository.save(productEntityToSave)).thenReturn(savedProductEntity);
        when(productMapper.productEntityToProductDTO(savedProductEntity)).thenReturn(productDTO);

        // Call the method and verify the result
        ProductDTO result = productService.saveProduct(productDTO);

        // Verify that methods are called correctly
        verify(productMapper).productDTOToProductEntity(productDTO);
        verify(productRepository).save(productEntityToSave);
        verify(productMapper).productEntityToProductDTO(savedProductEntity);

        // Verify the result
        assertThat(result).isEqualTo(productDTO);
    }

    @DisplayName("Test deleteProduct method")
    @Test
    public void testDeleteProduct() {
        long productId = 1L;
        Product existingProduct = new Product();

        // Set up mocks
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Call the method
        productService.deleteProduct(productId);

        // Verify that methods are called correctly
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }
}
