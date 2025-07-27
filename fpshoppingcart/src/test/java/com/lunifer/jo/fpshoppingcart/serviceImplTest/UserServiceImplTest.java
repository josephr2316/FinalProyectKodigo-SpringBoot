package com.lunifer.jo.fpshoppingcart.serviceImplTest;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {
        // Mocking
        UserDTO userDTO = new UserDTO();
        User userEntity = new User();
        when(userMapper.userDTOToUserEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Test
        UserDTO savedUser = userService.saveUser(userDTO);

        // Verify
        assertNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById() {
        // Mocking
        Long userId = 1L;
        User userEntity = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(new UserDTO());

        // Test
        UserDTO userDTO = userService.getUserById(userId);

        // Verify
        assertNotNull(userDTO);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser() {
        // Mocking
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userMapper.userEntityToUserDTO(existingUser)).thenReturn(userDTO);

        // Test
        UserDTO updatedUser = userService.updateUser(userId, userDTO);

        // Verify
        assertNotNull(updatedUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        // Mocking
        Long userId = 1L;
        User userEntity = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));

        // Test
        userService.deleteUser(userId);

        // Verify
        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    void findByUsername() {
        // Mocking
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(new User());

        // Test
        User user = userService.findByUsername(username);

        // Verify
        assertNotNull(user);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void saveUserEntity() {
        // Mocking
        User userEntity = new User();
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // Test
        User savedUser = userService.saveUser(userEntity);

        // Verify
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(userEntity);
    }
/*    @Test
    void initializeUserException() {
        // Mocking
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        // Test and Verify
        assertThrows(RuntimeException.class, () -> userService.);
    }*/
}
