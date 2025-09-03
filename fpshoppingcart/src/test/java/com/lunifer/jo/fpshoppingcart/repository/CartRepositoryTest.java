package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Cart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    private User testUser;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@test.com");
        testUser.setAddress("123 Test St");
        testUser.setPhoneNumber("1234567890");
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setActive(true);
        testUser.setRoles(Set.of(UserRol.USER));
        testUser = entityManager.persistAndFlush(testUser);

        // Create test cart
        testCart = new Cart();
        testCart.setUser(testUser);
        testCart.setTotalPrice(BigDecimal.ZERO);
        testCart = entityManager.persistAndFlush(testCart);
    }

    @Test
    void shouldFindCartByUserId() {
        // When
        Optional<Cart> found = cartRepository.findByUser_UserId(testUser.getUserId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUser().getUserId()).isEqualTo(testUser.getUserId());
        assertThat(found.get().getTotalPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void shouldReturnEmptyWhenCartNotFound() {
        // When
        Optional<Cart> found = cartRepository.findByUser_UserId(999L);

        // Then
        assertThat(found).isEmpty();
    }
}
