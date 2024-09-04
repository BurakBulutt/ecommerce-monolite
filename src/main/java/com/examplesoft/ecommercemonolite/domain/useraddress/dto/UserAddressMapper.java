package com.examplesoft.ecommercemonolite.domain.useraddress.dto;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.useraddress.entity.UserAddress;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAddressMapper {

    public static UserAddressDto toDto(UserAddress entity, UserDto user) {
        return UserAddressDto.builder()
                .title(entity.getTitle())
                .address(entity.getAddress())
                .user(user)
                .build();
    }

    public static UserAddress toEntity(UserAddress entity,UserAddressDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setAddress(dto.getAddress());
        entity.setUserId(dto.getUser().getId());

        return entity;
    }

}
