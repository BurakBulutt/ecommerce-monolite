package com.examplesoft.ecommercemonolite.domain.user.dto;

import com.examplesoft.ecommercemonolite.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .isActive(entity.getIsActive())
                .isVerified(entity.getIsVerified())
                .userType(entity.getUserType())
                .build();
    }

    public static User toEntity(User entity,UserDto dto) {
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setIsActive(dto.getIsActive());
        entity.setIsVerified(dto.getIsVerified());
        entity.setUserType(dto.getUserType());
        return entity;
    }
}
