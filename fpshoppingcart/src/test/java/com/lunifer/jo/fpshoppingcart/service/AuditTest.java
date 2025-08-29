package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateUserDTO;
import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldSetAuditFieldsWhenCreatingUser() {
        // Given
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("1234567890")
                .username("johndoe")
                .password("password123")
                .build();

        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setAddress("123 Main St");
        user.setPhoneNumber("1234567890");
        user.setUsername("johndoe");
        user.setPassword("encodedPassword");
        user.setActive(true);

        UserDTO userDTO = UserDTO.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phoneNumber("1234567890")
                .username("johndoe")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userMapper.toUser(createUserDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        // When
        UserDTO result = userService.createUser(createUserDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getAddress()).isEqualTo("123 Main St");
        assertThat(result.getPhoneNumber()).isEqualTo("1234567890");
        assertThat(result.getUsername()).isEqualTo("johndoe");
        assertThat(result.isActive()).isTrue();
        
        // Verify audit fields are present
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
        assertThat(result.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldUpdateAuditFieldsWhenUpdatingUser() {
        // Given
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setAddress("123 Main St");
        existingUser.setPhoneNumber("1234567890");
        existingUser.setUsername("johndoe");
        existingUser.setPassword("encodedPassword");
        existingUser.setActive(true);
        existingUser.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingUser.setUpdatedAt(LocalDateTime.now().minusDays(1));

        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Smith");
        updatedUser.setEmail("john.smith@example.com");
        updatedUser.setAddress("456 Oak St");
        updatedUser.setPhoneNumber("0987654321");
        updatedUser.setUsername("johnsmith");
        updatedUser.setPassword("encodedPassword");
        updatedUser.setActive(true);
        updatedUser.setCreatedAt(LocalDateTime.now().minusDays(1));
        updatedUser.setUpdatedAt(LocalDateTime.now());

        UserDTO userDTO = UserDTO.builder()
                .userId(userId)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .address("456 Oak St")
                .phoneNumber("0987654321")
                .username("johnsmith")
                .active(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toUserDTO(updatedUser)).thenReturn(userDTO);

        // When
        UserDTO result = userService.updateUser(userId, null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Smith");
        assertThat(result.getEmail()).isEqualTo("john.smith@example.com");
        assertThat(result.getAddress()).isEqualTo("456 Oak St");
        assertThat(result.getPhoneNumber()).isEqualTo("0987654321");
        assertThat(result.getUsername()).isEqualTo("johnsmith");
        assertThat(result.isActive()).isTrue();
        
        // Verify audit fields are present and updated
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
        assertThat(result.getCreatedAt()).isBefore(result.getUpdatedAt());
    }
}
