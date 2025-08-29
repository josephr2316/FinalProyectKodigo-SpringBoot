package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByUsername() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistent");

        // Then
        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldFindUserByEmail() {
        // Given
        User user = new User();
        user.setUsername("testuser2");
        user.setEmail("test2@example.com");
        user.setFirstName("Test2");
        user.setLastName("User2");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("test2@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test2@example.com");
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser2");
    }
}
