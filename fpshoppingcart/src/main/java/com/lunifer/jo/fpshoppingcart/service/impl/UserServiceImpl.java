package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import com.lunifer.jo.fpshoppingcart.exception.DuplicateResourceException;
import com.lunifer.jo.fpshoppingcart.exception.InvalidCredentialsException;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.exception.UnauthorizedException;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // For password hashing
    
    private final JwtService jwtService; // For JWT generation
    
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public PagedResponse<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return PagedResponse.of(users.map(userMapper::toUserDTO));
    }

        @Override
    public UserDTO createUser(CreateUserDTO dto) {
        // Validate that username doesn't exist
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new DuplicateResourceException("User", "username", dto.getUsername());
        }
        
        // Validate that email doesn't exist
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User", "email", dto.getEmail());
        }
        
        User user = userMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Hashing

        // Assign default role (USER) if not specified
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.addRole(UserRol.USER);
        }
        
        // Save and return the user
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        userMapper.updateUserFromDTO(dto, user);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User","username",username));
    }

    @Override
    public LoginResponseDTO login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }
        
        if (!user.isActive()) {
            throw new InvalidCredentialsException("Inactive user");
        }
        
        // Convert to DTO
        UserDTO userDTO = userMapper.toUserDTO(user);
        
        // Generate JWT token
        AuthResponse authResponse = jwtService.generateToken(user.getUsername());
        
        // Return login response with user data and JWT token
        return LoginResponseDTO.builder()
                .user(userDTO)
                .jwtToken(authResponse.token())
                .expiresAt(authResponse.expiresAt())
                .message("Login successful")
                .build();
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO getCurrentUser() {
        // Get current authenticated user from SecurityContext
        org.springframework.security.core.Authentication authentication = 
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getName().equals("anonymousUser")) {
            String username = authentication.getName();
            return getUserByUsername(username);
        }
        
        throw new UnauthorizedException("User not authenticated");
    }

    @Override
    public void assignDefaultRolesToUsersWithoutRoles() {
        // Find all users and assign default USER role if they don't have roles
        List<User> allUsers = userRepository.findAll();
        
        for (User user : allUsers) {
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.addRole(UserRol.USER);
                userRepository.save(user);
            }
        }
    }
}