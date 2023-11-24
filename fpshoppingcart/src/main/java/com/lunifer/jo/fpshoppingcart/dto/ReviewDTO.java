package com.lunifer.jo.fpshoppingcart.dto;


import lombok.Data;

@Data
public class ReviewDTO {
    private long reviewId;
    private long productId;
    private long userId;
    private String comment;
    private boolean likeDislike;
}
