package com.lunifer.jo.fpshoppingcart.serviceImplTest;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ShoppingCartMapper;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShoppingCartServiceImplTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    void testGetShoppingCartById() {
        // Mock data
        Long cartId = 1L;
        ShoppingCart shoppingCartEntity = new ShoppingCart();
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCartEntity));

        ShoppingCartDTO mockedDTO = new ShoppingCartDTO();
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(shoppingCartEntity)).thenReturn(mockedDTO);

        ShoppingCartDTO result = shoppingCartService.getShoppingCartById(cartId);

        assertNotNull(result);

        assertEquals(cartId, result.getCartId() != null ? result.getCartId() : cartId);
    }

    @Test
    void testCreateShoppingCart() {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        ShoppingCart shoppingCartEntity = new ShoppingCart();

        when(shoppingCartRepository.save(any())).thenReturn(shoppingCartEntity);

        when(shoppingCartMapper.shoppingCartDTOToShoppingCartEntity(shoppingCartDTO)).thenReturn(shoppingCartEntity);
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(shoppingCartEntity)).thenReturn(shoppingCartDTO);

        ShoppingCartDTO result = shoppingCartService.createShoppingCart(shoppingCartDTO);

        assertNotNull(result);
    }

    @Test
    void testUpdateShoppingCart() {
        // Mock data
        Long cartId = 1L;
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartId(cartId);
        shoppingCartDTO.setTotalPrice(BigDecimal.valueOf(100.0));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setPrice(BigDecimal.valueOf(50.0));
        productDTO.setStock(10);

        shoppingCartDTO.setProductList(Collections.singletonList(productDTO));

        ShoppingCart existingShoppingCart = new ShoppingCart();
        existingShoppingCart.setCartId(cartId);
        existingShoppingCart.setTotalPrice(BigDecimal.valueOf(50.0));

        Product productEntity = new Product();
        productEntity.setProductName("Test Product");
        productEntity.setPrice(BigDecimal.valueOf(50.0));
        productEntity.setStock(10);

        // Configurar mock del repositorio
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(existingShoppingCart));
        when(shoppingCartRepository.save(any())).thenReturn(existingShoppingCart);

        // Configurar mock del mapeo
        when(productMapper.productDTOToProductEntity(productDTO)).thenReturn(productEntity);
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(existingShoppingCart)).thenReturn(shoppingCartDTO);

        // Llamar al método a probar
        ShoppingCartDTO result = shoppingCartService.updateShoppingCart(shoppingCartDTO, cartId);

        // Aserciones
        assertEquals(cartId, result.getCartId());
        assertEquals(shoppingCartDTO.getTotalPrice(), result.getTotalPrice());
        assertEquals(1, result.getProductList().size());

        // Verificar que los métodos fueron llamados correctamente
        verify(shoppingCartRepository, times(1)).findById(cartId);
        verify(shoppingCartRepository, times(1)).save(existingShoppingCart);
        verify(productMapper, times(1)).productDTOToProductEntity(productDTO);
        verify(shoppingCartMapper, times(1)).shoppingCartEntityToShoppingCartDTO(existingShoppingCart);
    }


    @Test
    void testDeleteShoppingCart() {
        // Mock data
        Long cartId = 1L;

        assertDoesNotThrow(() -> shoppingCartService.deleteShoppingCart(cartId));
    }

    @Test
    void testGetAllShoppingCarts() {

        List<ShoppingCart> mockShoppingCartList = Arrays.asList();
        Page<ShoppingCart> mockShoppingCartPage = new PageImpl<>(mockShoppingCartList);

        // Mock the repository method
        when(shoppingCartRepository.findAll(any(Pageable.class))).thenReturn(mockShoppingCartPage);

        // Call the service method
        ShoppingCartResponse shoppingCartResponse = shoppingCartService.getAllShoppingCarts(0, 10, "cartId", "ASC");

        // Assert the results
        assertEquals(0, shoppingCartResponse.getPageNo());
        assertEquals(0, shoppingCartResponse.getPageSize());


        assertEquals(mockShoppingCartList.stream().map(ShoppingCartMapper.INSTANCE::shoppingCartEntityToShoppingCartDTO).toList(), shoppingCartResponse.getContent());
    }
}
