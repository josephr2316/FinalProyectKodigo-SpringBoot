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
    LoginResponseDTO login(LoginDTO dto); // Returns user data + JWT token
    void changePassword(Long userId, ChangePasswordDTO dto);
    UserDTO getCurrentUser(); // Get current authenticated user
    void assignDefaultRolesToUsersWithoutRoles(); // Assign default USER role to users without roles

}