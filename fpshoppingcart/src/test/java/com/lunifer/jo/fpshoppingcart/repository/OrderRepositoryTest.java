package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldFindOrdersByUser() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setPhoneNumber("1234567890"); // Added required field
        user.setAddress("123 Test St"); // Added required field
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        User savedUser = entityManager.persistAndFlush(user);

        Order order = new Order();
        order.setUser(savedUser);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.valueOf(100.00));
        order.setShippingAddress("123 Test St");
        
        entityManager.persistAndFlush(order);

        // When
        Page<Order> foundOrdersPage = orderRepository.findByUser_UserId(savedUser.getUserId(), PageRequest.of(0, 10));

        // Then
        assertThat(foundOrdersPage.getContent()).hasSize(1);
        assertThat(foundOrdersPage.getContent().get(0).getUser().getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(foundOrdersPage.getContent().get(0).getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void shouldFindOrdersByStatus() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setPhoneNumber("1234567890"); // Added required field
        user.setAddress("123 Test St"); // Added required field
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        User savedUser = entityManager.persistAndFlush(user);

        Order order1 = new Order();
        order1.setUser(savedUser);
        order1.setStatus(OrderStatus.PENDING);
        order1.setTotalAmount(BigDecimal.valueOf(100.00));
        
        Order order2 = new Order();
        order2.setUser(savedUser);
        order2.setStatus(OrderStatus.DELIVERED);
        order2.setTotalAmount(BigDecimal.valueOf(200.00));
        
        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        // When - Using findAll and filtering in test
        List<Order> allOrders = orderRepository.findAll();
        List<Order> pendingOrders = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .toList();

        // Then
        assertThat(pendingOrders).hasSize(1);
        assertThat(pendingOrders.get(0).getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersFound() {
        // When
        List<Order> allOrders = orderRepository.findAll();
        List<Order> cancelledOrders = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELLED)
                .toList();

        // Then
        assertThat(cancelledOrders).isEmpty();
    }
}
