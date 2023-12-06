package com.lunifer.jo.fpshoppingcart.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private long reviewId;
    private long productId;
    private long userId;
    private String comment;
    private boolean likeDislike;
}
