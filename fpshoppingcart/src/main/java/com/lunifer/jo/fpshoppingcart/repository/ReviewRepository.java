package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Page<Review> findByProduct_ProductId(Long productId, Pageable pageable);
    Page<Review> findByUser_UserId(Long userId, Pageable pageable);
     
}
