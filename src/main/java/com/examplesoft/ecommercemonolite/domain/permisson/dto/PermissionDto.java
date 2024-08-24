package com.examplesoft.ecommercemonolite.domain.permisson.dto;

import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private String id;
    private String name;
    private String description;
    private PermissionType permissionType;
}
