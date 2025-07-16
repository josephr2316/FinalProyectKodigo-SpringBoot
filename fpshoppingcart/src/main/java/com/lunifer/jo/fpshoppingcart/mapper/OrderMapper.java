package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userFullName", expression = "java(order.getUser().getFullName())")
    @Mapping(target = "canBeCancelled", expression = "java(order.canBeCancelled())")
    OrderDTO toOrderDTO(Order order);
}
