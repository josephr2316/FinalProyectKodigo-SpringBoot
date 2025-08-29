package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.*;
import org.springframework.data.domain.Pageable;

public interface UserService  {

    UserDTO getUserById(Long id);
    PagedResponse<UserDTO> getAllUsers(Pageable pageable);
    UserDTO createUser(CreateUserDTO dto);
    UserDTO updateUser(Long id, UpdateUserDTO dto);
    void deleteUser(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO login(LoginDTO dto); // Si no usas Spring Security/JWT nativo, sino autenticación manual
    void changePassword(Long userId, ChangePasswordDTO dto);
    UserDTO getCurrentUser(); // Get current authenticated user

}