package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewService {
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    void deleteReview(long reviewId);
    List<ReviewDTO> getAllReviewsByProductId(long productId);
    void deleteReviewByProducts(List<ProductDTO> productDTOS);
    void deleteReviewsByProductId(long productId);
}
