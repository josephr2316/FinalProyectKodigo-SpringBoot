package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.config.TestSecurityConfig;
import com.lunifer.jo.fpshoppingcart.entity.Cart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindCartByUserId() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        // When
        Optional<Cart> foundCart = cartRepository.findByUser_UserId(user.getUserId());

        // Then
        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(foundCart.get().getUser().getUsername()).isEqualTo("testuser");
    }

    @Test
    void shouldReturnEmptyWhenCartNotFound() {
        // When
        Optional<Cart> foundCart = cartRepository.findByUser_UserId(999L);

        // Then
        assertThat(foundCart).isEmpty();
    }
}
