package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Test saveReview method")
    @Test
    public void testSaveReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        Review reviewEntityToSave = new Review();

        // Set up mocks
        when(reviewMapper.reviewDTOToReviewEntity(reviewDTO)).thenReturn(reviewEntityToSave);

        // Call the method
        reviewService.saveReview(reviewDTO);

        // Verify that methods are called correctly
        verify(reviewMapper).reviewDTOToReviewEntity(reviewDTO);
        verify(reviewRepository).save(reviewEntityToSave);
    }

    @DisplayName("Test deleteReview method")
    @Test
    public void testDeleteReview() {
        long reviewId = 1L;
        Review existingReview = new Review();

        // Set up mocks
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));

        // Call the method
        reviewService.deleteReview(reviewId);

        // Verify that methods are called correctly
        verify(reviewRepository).existsById(reviewId);
        verify(reviewRepository).deleteById(reviewId);
    }

//    @DisplayName("Test deleteReview method with non-existent review")
//    @Test
//    public void testDeleteReview_NonExistentReview() {
//        long reviewId = 1L;
//
//        // Set up mocks
//        when(reviewRepository.existsById(reviewId)).thenReturn(false);
//
//        // Verify that attempting to delete a non-existent review results in an exception
//        assertThatThrownBy(() -> reviewService.deleteReview(reviewId))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("No se encontro review con ID " + reviewId);
//    }

    @DisplayName("Test getAllReviewsByProductId method")
    @Test
    public void testGetAllReviewsByProductId() {
        long productId = 1L;
        List<Review> reviewEntities = Collections.singletonList(new Review());
        List<ReviewDTO> expectedDTOs = Collections.singletonList(new ReviewDTO());

        // Set up mocks
        when(reviewRepository.findReviewsByProductId(productId)).thenReturn(reviewEntities);
        when(reviewMapper.reviewEntityToReviewDTO(any(Review.class))).thenReturn(new ReviewDTO());

        // Call the method and verify the result
        List<ReviewDTO> result = reviewService.getAllReviewsByProductId(productId);

        // Verify that methods are called correctly
        verify(reviewRepository).findReviewsByProductId(productId);
        verify(reviewMapper, times(reviewEntities.size())).reviewEntityToReviewDTO(any(Review.class));

        // Verify the result
        assertThat(result).isEqualTo(expectedDTOs);
    }
}
