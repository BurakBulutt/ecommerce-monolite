package com.examplesoft.ecommercemonolite.domain.permisson.dto;

import com.examplesoft.ecommercemonolite.domain.permisson.entity.Permission;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionMapper {

    public static PermissionDto toDto(Permission entity) {
        return PermissionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .permissionType(entity.getPermissionType())
                .build();
    }

    public static Permission toEntity(Permission entity,PermissionDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPermissionType(dto.getPermissionType());

        return entity;
    }
}
