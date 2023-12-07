package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
/*    @Mappings({
            @Mapping(target = "orderHistory", ignore = true),
            @Mapping(target = "reviewHistory", ignore = true)
    })*/
    UserDTO userEntityToUserDTO(User user);

    User userDTOToUserEntity(UserDTO userDTO);

}
