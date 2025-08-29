package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Cart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_UserId(Long userId);
    
}
