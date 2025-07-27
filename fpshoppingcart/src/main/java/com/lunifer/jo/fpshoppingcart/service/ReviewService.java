package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewService {
    ReviewDTO getReviewById(Long id);
    PagedResponse<ReviewDTO> getAllReviews(Pageable pageable);
    ReviewDTO createReview(CreateReviewDTO dto);
    void deleteReview(Long id);
}
