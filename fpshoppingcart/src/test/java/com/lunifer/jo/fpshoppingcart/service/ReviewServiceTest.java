package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateReviewDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewDTO reviewDTO;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setPrice(new BigDecimal("999.99"));

        review = new Review();
        review.setReviewId(1L);
        review.setRating(5);
        review.setComment("Great product!");
        review.setLikeDislike(true);
        review.setUser(user);
        review.setProduct(product);

        reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(1L);
        reviewDTO.setRating(5);
        reviewDTO.setComment("Great product!");
        reviewDTO.setLikeDislike(true);
        reviewDTO.setUserId(1L);
        reviewDTO.setProductId(1L);
    }

    @Test
    void shouldGetReviewById() {
        // Given
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewMapper.toReviewDTO(review)).thenReturn(reviewDTO);

        // When
        ReviewDTO result = reviewService.getReviewById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewId()).isEqualTo(1L);
        assertThat(result.getRating()).isEqualTo(5);
        assertThat(result.getComment()).isEqualTo("Great product!");
        verify(reviewRepository).findById(1L);
        verify(reviewMapper).toReviewDTO(review);
    }

    @Test
    void shouldThrowExceptionWhenReviewNotFound() {
        // Given
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reviewService.getReviewById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Review")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(reviewRepository).findById(1L);
        verify(reviewMapper, never()).toReviewDTO(any());
    }

    @Test
    void shouldGetAllReviews() {
        // Given
        Page<Review> reviewPage = new PageImpl<>(Collections.singletonList(review));
        when(reviewRepository.findAll(any(PageRequest.class))).thenReturn(reviewPage);
        when(reviewMapper.toReviewDTO(review)).thenReturn(reviewDTO);

        // When
        PagedResponse<ReviewDTO> result = reviewService.getAllReviews(PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getComment()).isEqualTo("Great product!");
        verify(reviewRepository).findAll(any(PageRequest.class));
        verify(reviewMapper).toReviewDTO(review);
    }

    @Test
    void shouldCreateReview() {
        // Given
        CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setRating(4);
        createReviewDTO.setComment("Good product");
        createReviewDTO.setLikeDislike(true);
        createReviewDTO.setProductId(1L);

        when(reviewMapper.toReview(createReviewDTO)).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toReviewDTO(review)).thenReturn(reviewDTO);

        // When
        ReviewDTO result = reviewService.createReview(createReviewDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getComment()).isEqualTo("Great product!");
        verify(reviewMapper).toReview(createReviewDTO);
        verify(reviewRepository).save(any(Review.class));
        verify(reviewMapper).toReviewDTO(review);
    }

    @Test
    void shouldDeleteReview() {
        // Given
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // When
        reviewService.deleteReview(1L);

        // Then
        verify(reviewRepository).findById(1L);
        verify(reviewRepository).delete(review);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentReview() {
        // Given
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reviewService.deleteReview(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Review")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).delete(any());
    }
}
