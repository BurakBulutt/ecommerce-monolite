package com.examplesoft.ecommercemonolite.domain.userpermission.dto;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.userpermission.entity.UserPermission;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPermissionMapper {

    public static UserPermissionDto toDto(UserPermission entity, UserDto userDto, PermissionDto permissionDto) {
        return UserPermissionDto.builder()
                .permission(permissionDto)
                .user(userDto)
                .build();
    }

    public static UserPermission toEntity(UserPermission entity,UserPermissionDto dto) {
        entity.setUserId(dto.getUser().getId());
        entity.setPermissionId(dto.getPermission().getId());
        return entity;
    }
}
