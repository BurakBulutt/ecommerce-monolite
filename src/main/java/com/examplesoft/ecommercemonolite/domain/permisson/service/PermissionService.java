package com.examplesoft.ecommercemonolite.domain.permisson.service;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    List<PermissionDto> getByPermissionType(PermissionType permissionType);

    List<PermissionDto> getPermissionByIdIn(Set<String> ids);

    PermissionDto getById(String id);

    PermissionDto save(PermissionDto permissionDto);

    void saveAll(List<PermissionDto> permissionDtos);
}
