package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findAllByActiveTrue(Pageable pageable);
    Page<Product> findAllByCategory_CategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

}
