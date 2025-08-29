package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.enums.PaymentStatus;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class InvoiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void shouldFindInvoiceByOrder() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        User savedUser = entityManager.persistAndFlush(user);

        Order order = new Order();
        order.setUser(savedUser);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.valueOf(100.00));
        
        Order savedOrder = entityManager.persistAndFlush(order);

        Invoice invoice = new Invoice();
        invoice.setOrder(savedOrder);
        invoice.setTotalAmount(BigDecimal.valueOf(100.00));
        invoice.setPaymentStatus(PaymentStatus.PENDING);
        invoice.setInvoiceNumber("INV-123");
        
        entityManager.persistAndFlush(invoice);

        // When
        Optional<Invoice> foundInvoice = invoiceRepository.findByOrder_OrderId(savedOrder.getOrderId());

        // Then
        assertThat(foundInvoice).isPresent();
        assertThat(foundInvoice.get().getOrder().getOrderId()).isEqualTo(savedOrder.getOrderId());
        assertThat(foundInvoice.get().getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
    }

    @Test
    void shouldFindInvoicesByPaymentStatus() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
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
        
        Order savedOrder1 = entityManager.persistAndFlush(order1);
        Order savedOrder2 = entityManager.persistAndFlush(order2);

        Invoice invoice1 = new Invoice();
        invoice1.setOrder(savedOrder1);
        invoice1.setTotalAmount(BigDecimal.valueOf(100.00));
        invoice1.setPaymentStatus(PaymentStatus.PENDING);
        invoice1.setInvoiceNumber("INV-123");
        
        Invoice invoice2 = new Invoice();
        invoice2.setOrder(savedOrder2);
        invoice2.setTotalAmount(BigDecimal.valueOf(200.00));
        invoice2.setPaymentStatus(PaymentStatus.PAID);
        invoice2.setInvoiceNumber("INV-124");
        
        entityManager.persistAndFlush(invoice1);
        entityManager.persistAndFlush(invoice2);

        // When - Using findAll and filtering in test
        List<Invoice> allInvoices = invoiceRepository.findAll();
        List<Invoice> pendingInvoices = allInvoices.stream()
                .filter(invoice -> invoice.getPaymentStatus() == PaymentStatus.PENDING)
                .toList();

        // Then
        assertThat(pendingInvoices).hasSize(1);
        assertThat(pendingInvoices.get(0).getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
    }

    @Test
    void shouldReturnEmptyWhenInvoiceNotFound() {
        // When
        Optional<Invoice> foundInvoice = invoiceRepository.findByOrder_OrderId(999L);

        // Then
        assertThat(foundInvoice).isEmpty();
    }
}
