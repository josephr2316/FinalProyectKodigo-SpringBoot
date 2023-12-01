package com.lunifer.jo.fpshoppingcart.serviceImplTest;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> productList = Arrays.asList(
                new Product(/* Set the necessary properties */),
                new Product(/* Set the necessary properties */)
        );
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> expectedProductDTOList = Arrays.asList(
                new ProductDTO(/* Set the expected properties */),
                new ProductDTO(/* Set the expected properties */)
        );
        when(productMapper.productEntityToProductDTO(any())).thenReturn(expectedProductDTOList.get(0), expectedProductDTOList.get(1));

        // Act
        List<ProductDTO> result = productService.getAllProducts();

        // Assert
        assertEquals(expectedProductDTOList, result);
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(2)).productEntityToProductDTO(any()); // Assuming two products in the list
    }

    @Test
    public void testGetProductById() {
        // Arrange
        long productId = 1L;
        Product product = new Product(/* Set the necessary properties */);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        ProductDTO expectedProductDTO = new ProductDTO(/* Set the expected properties */);
        when(productMapper.productEntityToProductDTO(product)).thenReturn(expectedProductDTO);

        // Act
        ProductDTO result = productService.getProductById(productId);

        // Assert
        assertEquals(expectedProductDTO, result);
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).productEntityToProductDTO(product);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteProduct_ProductNotFound() {
        // Arrange
        long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act y Assert
        productService.deleteProduct(productId);

    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        long productId = 1L;
        Product productToDelete = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productToDelete));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void saveProduct_WithValidProduct_ShouldNotThrowException() {
        // Arrange
        ProductDTO validProduct = createValidProductDTO();
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, productMapper, categoryMapper);

        // Act & Assert
        assertDoesNotThrow(() -> productService.saveProduct(validProduct));
    }

    @Test
    public void saveProduct_WithNullProductName_ShouldThrowException() {
        // Arrange
        ProductDTO productWithNullName = createValidProductDTO();
        productWithNullName.setProductName(null);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, productMapper, categoryMapper);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productWithNullName));
    }

    @Test
    public void saveProduct_WithZeroPrice_ShouldThrowException() {
        // Arrange
        ProductDTO productWithZeroPrice = createValidProductDTO();
        productWithZeroPrice.setPrice(BigDecimal.ZERO);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, productMapper, categoryMapper);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productWithZeroPrice));
    }

    @Test
    public void saveProduct_WithNegativeStock_ShouldThrowException() {
        // Arrange
        ProductDTO productWithNegativeStock = createValidProductDTO();
        productWithNegativeStock.setStock(-1);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, productMapper, categoryMapper);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productWithNegativeStock));
    }

    @Test
    public void saveProduct_WithNullCategory_ShouldThrowException() {
        // Arrange
        ProductDTO productWithNullCategory = createValidProductDTO();
        productWithNullCategory.setCategoryDTO(null);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, productMapper, categoryMapper);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productWithNullCategory));
    }

    // Helper method to create a valid ProductDTO for testing
    private ProductDTO createValidProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Valid Product");
        productDTO.setPrice(BigDecimal.TEN);
        productDTO.setStock(5);
        productDTO.setActive(true);
        productDTO.setCategoryDTO(Mockito.mock(CategoryDTO.class));
        return productDTO;
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setProductName("Updated Product");
        inputDTO.setDescription("Updated Description");
        inputDTO.setPrice(BigDecimal.TEN);
        inputDTO.setStock(20);
        inputDTO.setActive(true);

        Product existingProduct = new Product();
        existingProduct.setProductId(1L);
        existingProduct.setProductName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(BigDecimal.ONE);
        existingProduct.setStock(10);
        existingProduct.setActive(false);

        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.save(any())).thenReturn(existingProduct); // For simplicity, returning the same object
        Mockito.when(productMapper.productEntityToProductDTO(any())).thenReturn(inputDTO);

        // Act
        ProductDTO result = productService.updateProduct(inputDTO, 1L);

        // Assert
        assertEquals(inputDTO, result);
        assertEquals("Updated Product", existingProduct.getProductName());
        assertEquals("Updated Description", existingProduct.getDescription());
        assertEquals(BigDecimal.TEN, existingProduct.getPrice());
        assertEquals(20, existingProduct.getStock());
        assertEquals(true, existingProduct.isActive());
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).save(existingProduct);
        Mockito.verify(productMapper, Mockito.times(1)).productEntityToProductDTO(existingProduct);
    }
}
