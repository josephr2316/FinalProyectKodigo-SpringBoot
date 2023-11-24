package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.ReviewDTO;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "productId", source = "product.productId")
    ReviewDTO reviewEntityToReviewDTO(Review reviewEntity);

    @Mapping(target = "product.productId", source = "productId")
    Review reviewDTOToReviewEntity(ReviewDTO reviewDTO);
}
