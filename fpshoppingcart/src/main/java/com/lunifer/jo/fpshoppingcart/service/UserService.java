package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserResponse getAllUsers(int page, int pageSize, String sortBy, String sortDir);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);
}
