package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ShoppingCartMapper;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @DisplayName("Test getShoppingCartById method")
    @Test
    public void testGetShoppingCartById() {
        // Given
        long cartId = 1L;
        ShoppingCart shoppingCartEntity = new ShoppingCart();
        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO();

        when(shoppingCartRepository.findById(cartId)).thenReturn(java.util.Optional.of(shoppingCartEntity));
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(shoppingCartEntity)).thenReturn(expectedShoppingCartDTO);

        // When
        ShoppingCartDTO result = shoppingCartService.getShoppingCartById(cartId);

        // Then
        assertThat(result).isEqualTo(expectedShoppingCartDTO);
        verify(shoppingCartRepository).findById(cartId);
        verify(shoppingCartMapper).shoppingCartEntityToShoppingCartDTO(shoppingCartEntity);
    }

    @DisplayName("Test createShoppingCart method")
    @Test
    public void testCreateShoppingCart() {
        // Given
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        ShoppingCart shoppingCartEntity = new ShoppingCart();
        ShoppingCart savedShoppingCartEntity = new ShoppingCart();
        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO();

        when(shoppingCartMapper.shoppingCartDTOToShoppingCartEntity(shoppingCartDTO)).thenReturn(shoppingCartEntity);
        when(shoppingCartRepository.save(shoppingCartEntity)).thenReturn(savedShoppingCartEntity);
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(savedShoppingCartEntity)).thenReturn(expectedShoppingCartDTO);

        // When
        ShoppingCartDTO result = shoppingCartService.createShoppingCart(shoppingCartDTO);

        // Then
        assertThat(result).isEqualTo(expectedShoppingCartDTO);
        verify(shoppingCartMapper).shoppingCartDTOToShoppingCartEntity(shoppingCartDTO);
        verify(shoppingCartRepository).save(shoppingCartEntity);
        verify(shoppingCartMapper).shoppingCartEntityToShoppingCartDTO(savedShoppingCartEntity);
    }

    @DisplayName("Test updateShoppingCart method")
    @Test
    public void testUpdateShoppingCart() {
        // Given
        long cartId = 1L;
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        ShoppingCart existingShoppingCartEntity = new ShoppingCart();
        ShoppingCart updatedShoppingCartEntity = new ShoppingCart();
        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO();

        when(shoppingCartRepository.findById(cartId)).thenReturn(java.util.Optional.of(existingShoppingCartEntity));
        when(productMapper.productDTOToProductEntity(any())).thenReturn(new Product());
        when(shoppingCartRepository.save(existingShoppingCartEntity)).thenReturn(updatedShoppingCartEntity);
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(updatedShoppingCartEntity)).thenReturn(expectedShoppingCartDTO);

        // When
        ShoppingCartDTO result = shoppingCartService.updateShoppingCart(shoppingCartDTO, cartId);

        // Then
        assertThat(result).isEqualTo(expectedShoppingCartDTO);
        verify(shoppingCartRepository).findById(cartId);
        verify(productMapper, times(shoppingCartDTO.getProductList().size())).productDTOToProductEntity(any());
        verify(shoppingCartRepository).save(existingShoppingCartEntity);
        verify(shoppingCartMapper).shoppingCartEntityToShoppingCartDTO(updatedShoppingCartEntity);
    }

    @DisplayName("Test deleteShoppingCart method")
    @Test
    public void testDeleteShoppingCart() {
        // Given
        long cartId = 1L;

        // When
        shoppingCartService.deleteShoppingCart(cartId);

        // Then
        verify(shoppingCartRepository).deleteById(cartId);
    }

    @DisplayName("Test getAllShoppingCarts method")
    @Test
    public void testGetAllShoppingCarts() {
        // Given
        ShoppingCart shoppingCartEntity = new ShoppingCart();
        ShoppingCartDTO expectedShoppingCartDTO = new ShoppingCartDTO();

        when(shoppingCartRepository.findAll()).thenReturn(Collections.singletonList(shoppingCartEntity));
        when(shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(shoppingCartEntity)).thenReturn(expectedShoppingCartDTO);

        // When
        //java.util.List<ShoppingCartDTO> result = shoppingCartService.getAllShoppingCarts();

        // Then
       // assertThat(result).containsExactly(expectedShoppingCartDTO);
        verify(shoppingCartRepository).findAll();
        verify(shoppingCartMapper).shoppingCartEntityToShoppingCartDTO(shoppingCartEntity);
    }
}
