//package com.lunifer.jo.fpshoppingcart.service.impl;
//
//import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
//import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
//import com.lunifer.jo.fpshoppingcart.entity.Order;
//import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
//import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class OrderServiceImplTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private OrderMapper orderMapper;
//
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @DisplayName("Test updateOrder method")
//    @Test
//    public void testUpdateOrder() {
//        long orderId = 1L;
//        OrderDTO orderDTO = new OrderDTO();
//        List<ProductDTO> productDTOListTest = new ArrayList<>();
//        orderDTO.setProductList(productDTOListTest);
//
//        // Set up mocks
//        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(new Order()));
//        when(orderRepository.save(any())).thenReturn(new Order());
//
//        // Utiliza eq() para especificar un argumento específico
//        when(orderMapper.orderEntityToOrderDTO(eq(new Order()))).thenReturn(orderDTO);
//
//        // Call the method and verify the result
//        OrderDTO result = orderService.updateOrder(orderId, orderDTO);
//
//        // Verify that methods are called correctly
//        verify(orderRepository).findById(orderId);
//        verify(orderRepository).save(any());
//
//        // Utiliza eq() también en la verificación del mock
//
//        // Verify the result
//        assertThat(result).isEqualTo(orderDTO);
//    }
//
//}
