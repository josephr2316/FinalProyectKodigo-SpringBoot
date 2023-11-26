package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
@Service // Le falto esto a Luis
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO, long productId) {

        Review reviewEntity = reviewMapper.reviewDTOToReviewEntity(reviewDTO);


        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("No se encontro el producto: " + productId));

        reviewEntity = reviewRepository.save(reviewEntity);


        return reviewMapper.reviewEntityToReviewDTO(reviewEntity);
    }

    @Override
    public void deleteReview(long reviewId, long productId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDTO> getAllReviewsByProductId(long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        return reviews.stream()
                .map(reviewMapper::reviewEntityToReviewDTO)
                .collect(Collectors.toList());
    }

}
