package com.lunifer.jo.fpshoppingcart.serviceImplTest;

import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OrderMapper orderMapper;

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
        orderDTO.setProductList(new HashSet<>());

        Order existingOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        // Act
        OrderDTO result = orderService.updateOrder(orderId, orderDTO);

        // Assert
        assertNotNull(result);
        assertEquals(orderDTO.getOrderId(), orderId);

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

        List<Order> mockOrderList = Arrays.asList();
        Page<Order> mockOrderPage = new PageImpl<>(mockOrderList);


        when(orderRepository.findAll(any(Pageable.class))).thenReturn(mockOrderPage);


        OrderResponse orderResponse = orderService.getAllOrders(0, 10, "orderId", "ASC");


        assertEquals(0, orderResponse.getPageNo());
        assertEquals(0, orderResponse.getPageSize());

    }
}
