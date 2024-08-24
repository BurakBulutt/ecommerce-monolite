package com.examplesoft.ecommercemonolite.domain.userpermission.repo;

import com.examplesoft.ecommercemonolite.domain.userpermission.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, String> {
    List<UserPermission> findAllByUserId(String userId);
}
