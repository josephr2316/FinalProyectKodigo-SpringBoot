package com.lunifer.jo.fpshoppingcart.serviceImpl;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderById() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setOrderId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        OrderDTO result = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    public void testCreateOrder() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        Order savedOrder = new Order();

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    @Test
    public void testUpdateOrder() {
        // Arrange
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);

        // Inicializar la lista de productos para evitar el NullPointerException
        orderDTO.setProductList(new ArrayList<>());

        Order existingOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        // Act
        OrderDTO result = orderService.updateOrder(orderId, orderDTO);

        // Assert
        assertNotNull(result);
        assertEquals(orderDTO.getOrderId(),orderId );

    }


    @Test
    public void testDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<Order> orders = Collections.singletonList(new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<OrderDTO> result = orderService.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(orders.size(), result.size());
        verify(orderRepository, times(1)).findAll();
    }
}
