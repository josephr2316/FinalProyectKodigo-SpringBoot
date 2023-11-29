package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
<<<<<<< HEAD
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
=======
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;
>>>>>>> security-jwt

import java.util.List;

public interface UserService  {
    UserDTO saveUser(UserDTO userDTO);

    UserResponse getAllUsers(int page, int pageSize, String sortBy, String sortDir);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    User findByUsername(String username);

    User saveUser(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void initializeUser();

}