package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
//<<<<< HEAD
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//=======
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;
//>>>>>>> security-jwt

import java.util.List;

public interface UserService  {
    
    UserDTO getUserById(Long id);
    PagedResponse<UserDTO> getAllUsers(Pageable pageable);
    UserDTO createUser(CreateUserDTO dto);
    UserDTO updateUser(Long id, UpdateUserDTO dto);
    void deleteUser(Long id);

        // Métodos adicionales recomendados:
    UserDTO getUserByUsername(String username);
    UserDTO login(LoginDTO dto); // Si no usas Spring Security/JWT nativo, sino autenticación manual
    void changePassword(Long userId, ChangePasswordDTO dto);

}