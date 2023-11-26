package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.saveReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<Void> deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsByProductId(@PathVariable long productId) {
        List<ReviewDTO> reviews = reviewService.getAllReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
