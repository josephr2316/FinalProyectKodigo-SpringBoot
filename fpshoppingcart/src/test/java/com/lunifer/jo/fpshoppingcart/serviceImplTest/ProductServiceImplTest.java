//package com.lunifer.jo.fpshoppingcart.serviceImplTest;
//
//import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
//import com.lunifer.jo.fpshoppingcart.entity.Product;
//import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
//import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
//import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
//import com.lunifer.jo.fpshoppingcart.service.impl.ProductServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class ProductServiceImplTest {
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @Mock
//    private CategoryMapper categoryMapper;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//    @Test
//    public void testGetAllProducts() {
//        // Arrange
//        List<Product> productList = Arrays.asList(
//                new Product(/* Set the necessary properties */),
//                new Product(/* Set the necessary properties */)
//        );
//        when(productRepository.findAll()).thenReturn(productList);
//
//        List<ProductDTO> expectedProductDTOList = Arrays.asList(
//                new ProductDTO(/* Set the expected properties */),
//                new ProductDTO(/* Set the expected properties */)
//        );
//        when(productMapper.productEntityToProductDTO(any())).thenReturn(expectedProductDTOList.get(0), expectedProductDTOList.get(1));
//
//        // Act
//        List<ProductDTO> result = productService.getAllProducts();
//
//        // Assert
//        assertEquals(expectedProductDTOList, result);
//        verify(productRepository, times(1)).findAll();
//        verify(productMapper, times(2)).productEntityToProductDTO(any()); // Assuming two products in the list
//    }
//
//    @Test
//    public void testGetProductById() {
//        // Arrange
//        long productId = 1L;
//        Product product = new Product(/* Set the necessary properties */);
//        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
//
//        ProductDTO expectedProductDTO = new ProductDTO(/* Set the expected properties */);
//        when(productMapper.productEntityToProductDTO(product)).thenReturn(expectedProductDTO);
//
//        // Act
//        ProductDTO result = productService.getProductById(productId);
//
//        // Assert
//        assertEquals(expectedProductDTO, result);
//        verify(productRepository, times(1)).findById(productId);
//        verify(productMapper, times(1)).productEntityToProductDTO(product);
//    }
//
//    @Test(expected = NoSuchElementException.class)
//    public void testDeleteProduct_ProductNotFound() {
//        // Arrange
//        long productId = 1L;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // Act y Assert
//        productService.deleteProduct(productId);
//
//    }
//
//    @Test
//    public void testDeleteProduct() {
//        // Arrange
//        long productId = 1L;
//        Product productToDelete = new Product();
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(productToDelete));
//
//        productService.deleteProduct(productId);
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).deleteById(productId);
//    }
//}
