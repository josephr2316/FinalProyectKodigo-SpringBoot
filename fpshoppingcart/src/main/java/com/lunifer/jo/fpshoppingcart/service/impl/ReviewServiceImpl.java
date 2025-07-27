package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CreateReviewDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::reviewEntityToReviewDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public PagedResponse<ReviewDTO> getAllReviews(Pageable pageable) {
        return null;
    }

    @Override
    public ReviewDTO createReview(CreateReviewDTO dto) {
        return null;
    }

    @Override
    public PagedResponse<ReviewDTO> getAllReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return PagedResponse.of(reviews.map(reviewMapper::reviewEntityToReviewDTO));
    }

    @Override
    public ReviewDTO createReview(CreateReviewDTO dto) {
        Review review = reviewMapper.createReviewDTOToReview(dto);
        return reviewMapper.reviewEntityToReviewDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        reviewRepository.delete(review);
    }
}