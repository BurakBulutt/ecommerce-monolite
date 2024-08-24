package com.examplesoft.ecommercemonolite.domain.permisson.repo;

import com.examplesoft.ecommercemonolite.domain.permisson.entity.Permission;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,String> {
    List<Permission> findAllByPermissionType(PermissionType permissionType);
    Optional<Permission> findByName(String name);
}
