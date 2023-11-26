package com.lunifer.jo.fpshoppingcart.repository;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("JUnit test for saving order")
    @Test
    public void givenOrder_whenSaved_thenReturnSavedOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setProductList(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderId()).isNotNull();
    }

    @DisplayName("JUnit test for updating order")
    @Test
    public void givenOrder_whenUpdated_thenReturnUpdatedOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setProductList(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        order.setStatus(OrderStatus.DELIVERED);
        Order updatedOrder = orderRepository.save(order);

        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @DisplayName("JUnit test for deleting order")
    @Test
    public void givenOrder_whenDeleted_thenOrderShouldNotExist() {
        Order order = new Order();
        order.setUserId(1L);
        order.setProductList(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        orderRepository.deleteById(order.getOrderId());

        Optional<Order> deletedOrder = orderRepository.findById(order.getOrderId());
        assertThat(deletedOrder).isNotPresent();
    }
}
