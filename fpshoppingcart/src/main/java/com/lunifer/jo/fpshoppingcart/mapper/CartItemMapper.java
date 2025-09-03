package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.CartItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "cartId", source = "cart.cartId")
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "availableStock", source = "product.stock")
    @Mapping(target = "subtotal", expression = "java(cartItem.getSubtotal())")
    @Mapping(target = "validQuantity", expression = "java(cartItem.isValidQuantity())")
    @Mapping(target = "productImageUrl", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CartItemDTO toCartItemDTO(CartItem cartItem);
}
