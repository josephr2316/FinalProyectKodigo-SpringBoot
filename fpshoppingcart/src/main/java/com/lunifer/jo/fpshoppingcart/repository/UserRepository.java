package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    //@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.shoppingCartHistory sc LEFT JOIN FETCH u.orderHistory o")
    Page<User> findAll(Pageable pageable);

}

