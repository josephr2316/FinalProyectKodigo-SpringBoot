package com.lunifer.jo.fpshoppingcart.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long reviewId;
    private Long productId;
    private String productName;
    private Long userId;
    private String username;
    private String userFullName;
    private String comment;
    private int rating;
    private boolean likeDislike;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean positive;
    private boolean negative;
    private boolean neutral;
}
