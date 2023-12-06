package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDTO productEntityToProductDTO(Product productEntity);
    Product productDTOToProductEntity(ProductDTO productDTO);
    //CategoryDTO categoryEntityToCategoryDTO(Category category);

}
