package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setActive(true);
    }

    @Test
    void shouldGetUserById() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        // When
        UserDTO result = userService.getUserById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).findById(1L);
        verify(userMapper).toUserDTO(user);
    }

    @Test
    void shouldGetUserByUsername() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        // When
        UserDTO result = userService.getUserByUsername("testuser");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).findByUsername("testuser");
        verify(userMapper).toUserDTO(user);
    }
}