package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity → DTO
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringList")
    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserDTO toUserDTO(User user);

    // DTO → Entity
    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringListToRoles")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orderHistory", ignore = true)
    @Mapping(target = "reviewHistory", ignore = true)
    @Mapping(target = "cart", ignore = true)
    User toUser(UserDTO userDTO);

    // Create DTO → Entity
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "orderHistory", ignore = true)
    @Mapping(target = "reviewHistory", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(CreateUserDTO dto);

    // Update DTO → Entity (update in place)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orderHistory", ignore = true)
    @Mapping(target = "reviewHistory", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromDTO(UpdateUserDTO dto, @MappingTarget User user);

    // MapStruct helpers for roles
    @Named("rolesToStringList")
    default List<String> rolesToStringList(Set<UserRol> roles) {
        return roles != null ? roles.stream().map(Enum::name).collect(Collectors.toList()) : null;
    }

    @Named("stringListToRoles")
    default Set<UserRol> stringListToRoles(List<String> roles) {
        return roles != null ? roles.stream().map(UserRol::valueOf).collect(Collectors.toSet()) : null;
    }
}