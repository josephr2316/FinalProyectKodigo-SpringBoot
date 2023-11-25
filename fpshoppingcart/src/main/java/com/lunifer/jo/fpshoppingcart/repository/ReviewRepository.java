package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findReviewsByProductId(Long productId);
}
