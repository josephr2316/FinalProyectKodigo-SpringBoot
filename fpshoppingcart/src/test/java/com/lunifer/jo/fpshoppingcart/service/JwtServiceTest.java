package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private JwtService jwtService;

    private User user;

    @BeforeEach
    void setUp() {
        // Set test values for JWT configuration
        ReflectionTestUtils.setField(jwtService, "SECRET", "dGVzdF9zZWNyZXRfa2V5X2Zvcl90ZXN0aW5nX3B1cnBvc2VzX29ubHlfbXVzdF9iZV9sb25nX2Vub3VnaF9mb3Jfc2VjdXJpdHk=");
        ReflectionTestUtils.setField(jwtService, "EXPIRATION_TIME", 60L);

        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
    }

    @Test
    void shouldGenerateToken() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // When
        AuthResponse authResponse = jwtService.generateToken("testuser");

        // Then
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.token()).isNotNull();
        assertThat(authResponse.token()).isNotEmpty();
        assertThat(authResponse.expiresAt()).isGreaterThan(0);
    }

    @Test
    void shouldExtractUsername() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        AuthResponse authResponse = jwtService.generateToken("testuser");

        // When
        String extractedUsername = jwtService.extractUsername(authResponse.token());

        // Then
        assertThat(extractedUsername).isEqualTo("testuser");
    }

    @Test
    void shouldValidateToken() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        AuthResponse authResponse = jwtService.generateToken("testuser");

        // When
        Boolean isValid = jwtService.validateToken(authResponse.token());

        // Then
        assertThat(isValid).isTrue();
    }
}
