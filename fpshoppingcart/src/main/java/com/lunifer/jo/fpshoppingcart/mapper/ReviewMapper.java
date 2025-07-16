package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userFullName", expression = "java(review.getUser().getFullName())")
    @Mapping(target = "positive", expression = "java(review.isPositive())")
    @Mapping(target = "negative", expression = "java(review.isNegative())")
    @Mapping(target = "neutral", expression = "java(review.isNeutral())")
  
