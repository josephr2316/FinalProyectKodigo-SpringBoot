package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Entity → DTO
    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.categoryName")
    ProductDTO toProductDTO(Product product);

    // Create DTO → Entity
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "category.categoryId", source = "categoryId")
    Product toProduct(CreateProductDTO dto);

    // Update DTO → update Entity (update in place)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDTO(UpdateProductDTO dto, @MappingTarget Product product);
}
