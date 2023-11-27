package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service // Le falto esto a Luis
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public void saveReview(ReviewDTO reviewDTO) {
        Review reviewEntity = reviewMapper.reviewDTOToReviewEntity(reviewDTO);
        reviewRepository.save(reviewEntity);
    }

    @Override
    public void deleteReview(long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new EntityNotFoundException("No se encontro review con ID " + reviewId);
        }

    }

    @Override
    public List<ReviewDTO> getAllReviewsByProductId(long productId) {
        List<Review> reviewEntities = reviewRepository.findReviewsByProductId(productId);
        return reviewEntities.stream()
                .map(reviewMapper::reviewEntityToReviewDTO)
                .collect(Collectors.toList());
    }
}
