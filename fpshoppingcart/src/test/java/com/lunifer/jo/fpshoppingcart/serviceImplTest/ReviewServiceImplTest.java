//package com.lunifer.jo.fpshoppingcart.serviceImplTest;
//
//import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
//import com.lunifer.jo.fpshoppingcart.entity.Product;
//import com.lunifer.jo.fpshoppingcart.entity.Review;
//import com.lunifer.jo.fpshoppingcart.entity.User;
//import com.lunifer.jo.fpshoppingcart.mapper.ReviewMapper;
//import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
//import com.lunifer.jo.fpshoppingcart.service.impl.ReviewServiceImpl;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ReviewServiceImplTest {
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private ReviewMapper reviewMapper;
//
//    @InjectMocks
//    private ReviewServiceImpl reviewService;
//
//    @Test
//    void testSaveReview() {
//        ReviewDTO reviewDTO = new ReviewDTO();
//        Review reviewEntityToSave = new Review();
//
//        when(reviewMapper.reviewDTOToReviewEntity(reviewDTO)).thenReturn(reviewEntityToSave);
//        when(reviewRepository.save(reviewEntityToSave)).thenReturn(reviewEntityToSave);
//        when(reviewMapper.reviewEntityToReviewDTO(reviewEntityToSave)).thenReturn(reviewDTO);
//
//        ReviewDTO result = reviewService.saveReview(reviewDTO);
//
//        assertNotNull(result);
//        verify(reviewRepository, times(1)).save(reviewEntityToSave);
//        verify(reviewMapper, times(1)).reviewEntityToReviewDTO(reviewEntityToSave);
//    }
//
//    @Test
//    void testDeleteReview() {
//        long reviewId = 1L;
//        Review reviewEntityToDelete = new Review();
//
//        when(reviewRepository.existsById(reviewId)).thenReturn(true);
//
//
//        reviewService.deleteReview(reviewId);
//
//        verify(reviewRepository, times(1)).existsById(reviewId);
//        verify(reviewRepository, times(1)).deleteById(reviewId);
//    }
//
//    @Test
//    void testDeleteReview_ReviewNotFound() {
//        long reviewId = 1L;
//        when(reviewRepository.existsById(reviewId)).thenReturn(false);
//
//        assertThrows(EntityNotFoundException.class, () -> {
//            reviewService.deleteReview(reviewId);
//        });
//    }
//
//    @Test
//    void testGetAllReviewsByProductId() {
//        long productId = 1L;
//        List<Review> reviewEntities = Arrays.asList(
//                new Review(1L, new Product(), "Comment 1", true, new User()),
//                new Review(2L, new Product(), "Comment 2", false, new User())
//        );
//
//        when(reviewRepository.findReviewsByProductId(productId)).thenReturn(reviewEntities);
//
//        List<ReviewDTO> expectedReviewDTOList = Arrays.asList(
//                new ReviewDTO(),
//                new ReviewDTO()
//        );
//
//        when(reviewMapper.reviewEntityToReviewDTO(any()))
//                .thenReturn(expectedReviewDTOList.get(0), expectedReviewDTOList.get(1));
//
//        List<ReviewDTO> result = reviewService.getAllReviewsByProductId(productId);
//
//        assertEquals(expectedReviewDTOList, result);
//        verify(reviewRepository, times(1)).findReviewsByProductId(productId);
//        verify(reviewMapper, times(2)).reviewEntityToReviewDTO(any());
//    }
//}
