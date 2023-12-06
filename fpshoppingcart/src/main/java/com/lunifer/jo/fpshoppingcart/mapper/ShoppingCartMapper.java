package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ShoppingCartMapper {

    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    ShoppingCartDTO shoppingCartEntityToShoppingCartDTO(ShoppingCart shoppingCart);

    ShoppingCart shoppingCartDTOToShoppingCartEntity(ShoppingCartDTO shoppingCartDTO);



}
