package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByUserUserId(long userId);
    @Modifying
    @Query("DELETE FROM ShoppingCart s WHERE :product MEMBER OF s.productList")
    void deleteByProduct(@Param("product") Product product);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.productList p WHERE p IN :productList")
    List<Order> findDistinctByProductsIn(@Param("productList") List<Product> products);
}
