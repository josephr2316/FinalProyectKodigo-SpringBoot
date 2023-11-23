package com.lunifer.jo.fpshoppingcart.repositories;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
