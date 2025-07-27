package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDTO>> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getReviewById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ReviewDTO>>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getAllReviews(PageRequest.of(page, size))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(@RequestBody CreateReviewDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Review created successfully", reviewService.createReview(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully", null));
    }
}
