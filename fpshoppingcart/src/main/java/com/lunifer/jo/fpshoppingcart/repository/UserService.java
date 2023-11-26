package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User, String> {
    User findByUsername(String username);
}
