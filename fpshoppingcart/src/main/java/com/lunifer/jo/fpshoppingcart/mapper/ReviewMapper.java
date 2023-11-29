package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "userId", source = "user.userId")
    ReviewDTO reviewEntityToReviewDTO(Review reviewEntity);

    @Mapping(target = "product.productId", source = "productId")
    @Mapping(target = "user.userId", source = "userId")
    Review reviewDTOToReviewEntity(ReviewDTO reviewDTO);
}
