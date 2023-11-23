package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO saveReview(ReviewDTO reviewDTO, long productId);
    void deleteReview(long reviewId, long productId);
    List<ReviewDTO> getAllReviewsByProductId(long productId);
}
