package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateOrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.OrderServiceImpl;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderDTO orderDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");

        order = new Order();
        order.setOrderId(1L);
        order.setUser(user);
        order.setTotalAmount(new BigDecimal("999.99"));
        order.setStatus(OrderStatus.PENDING);
        orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setUserId(1L);
        orderDTO.setTotalAmount(new BigDecimal("999.99"));
        orderDTO.setStatus("PENDING");
    }

    @Test
    void shouldGetOrderById() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        // When
        OrderDTO result = orderService.getOrderById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(1L);
        assertThat(result.getTotalAmount()).isEqualTo(new BigDecimal("999.99"));
        assertThat(result.getStatus()).isEqualTo("PENDING");
        verify(orderRepository).findById(1L);
        verify(orderMapper).toOrderDTO(order);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> orderService.getOrderById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(orderRepository).findById(1L);
        verify(orderMapper, never()).toOrderDTO(any());
    }

    @Test
    void shouldGetAllOrders() {
        // Given
        Page<Order> orderPage = new PageImpl<>(Collections.singletonList(order));
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(orderPage);
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        // When
        PagedResponse<OrderDTO> result = orderService.getAllOrders(PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTotalAmount()).isEqualTo(new BigDecimal("999.99"));
        verify(orderRepository).findAll(any(PageRequest.class));
        verify(orderMapper).toOrderDTO(order);
    }

    @Test
    void shouldCreateOrder() {
        // Given
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setUserId(1L);
        createOrderDTO.setShippingAddress("123 Main St, City, State 12345");

        when(orderMapper.toOrder(createOrderDTO)).thenReturn(order);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        // When
        OrderDTO result = orderService.createOrder(createOrderDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalAmount()).isEqualTo(new BigDecimal("999.99"));
        verify(orderMapper).toOrder(createOrderDTO);
        verify(userRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).toOrderDTO(order);
    }

    @Test
    void shouldUpdateOrderStatus() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toOrderDTO(order)).thenReturn(orderDTO);

        // When
        OrderDTO result = orderService.updateOrderStatus(1L, "PROCESSING");

        // Then
        assertThat(result).isNotNull();
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderDTO(order);
        // Verify that the status was set to PROCESSING (since COMPLETED doesn't exist)
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    void shouldCancelOrder() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When
        orderService.cancelOrder(1L);

        // Then
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
        // Verify that the status was set to CANCELLED
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void shouldThrowExceptionWhenCancellingNonExistentOrder() {
        // Given
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> orderService.cancelOrder(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }
}
