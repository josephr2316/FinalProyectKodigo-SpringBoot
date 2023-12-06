package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductMapper productMapper;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ProductMapper productMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.productMapper = productMapper;
    }


    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review reviewEntity = reviewMapper.reviewDTOToReviewEntity(reviewDTO);
        reviewEntity = reviewRepository.save(reviewEntity);
        return reviewMapper.reviewEntityToReviewDTO(reviewEntity);
    }

    @Override
    @Transactional
    public void deleteReview(long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new EntityNotFoundException("No se encontro review con ID " + reviewId);
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
    public void deleteReviewByProducts(List<ProductDTO> productDTOS) {
        for (ProductDTO productDTO : productDTOS){
            Product product = productMapper.productDTOToProductEntity(productDTO);
            reviewRepository.deleteAllByProduct(product);
        }

    }
}
