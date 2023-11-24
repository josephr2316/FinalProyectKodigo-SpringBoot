package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    void saveReview(ReviewDTO reviewDTO);
    void deleteReview(long reviewId);
    List<ReviewDTO> getAllReviewsByProductId(long productId);
}
