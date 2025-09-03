package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Methods optimized with EntityGraph to load categories
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"category"})
    Optional<Product> findById(Long id);
    
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAllByActiveTrue(Pageable pageable);
    
    Page<Product> findAllByCategory_CategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

}
