package com.lunifer.jo.fpshoppingcart.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
    private long reviewId;
    private long productId;
    private long userId;
    private String comment;
    private boolean likeDislike;
}
