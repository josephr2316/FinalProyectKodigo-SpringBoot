package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Lazy
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;


    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review reviewEntity = reviewMapper.reviewDTOToReviewEntity(reviewDTO);
        reviewEntity = reviewRepository.save(reviewEntity);
        return reviewMapper.reviewEntityToReviewDTO(reviewEntity);
    }

    @Override
    public void deleteReview(long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new EntityNotFoundException("Cannot find Review " + reviewId);
        }

    }

    @Override
    @Transactional
    public List<ReviewDTO> getAllReviewsByProductId(long productId) {
        List<Review> reviewEntities = reviewRepository.findReviewsByProductId(productId);
        return reviewEntities.stream()
                .map(reviewMapper::reviewEntityToReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReviewsByProductId(long productId) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(productId);

        for (Review review : reviews) {
            reviewRepository.delete(review);
        }
    }

}
