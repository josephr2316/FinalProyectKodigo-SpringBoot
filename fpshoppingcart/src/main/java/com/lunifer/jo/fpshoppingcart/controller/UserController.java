package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // Para hash de password

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userEntityToUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public PagedResponse<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return PagedResponse.of(users.map(userMapper::userEntityToUserDTO));
    }

    @Override
    public UserDTO createUser(CreateUserDTO dto) {
        User user = userMapper.createUserDTOToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Hashing
        return userMapper.userEntityToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.updateUserFromDTO(dto, user);
        return userMapper.userEntityToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userEntityToUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return userMapper.userEntityToUserDTO(user);
        // Aquí deberías retornar también un JWT/token si usas autenticación basada en tokens.
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}