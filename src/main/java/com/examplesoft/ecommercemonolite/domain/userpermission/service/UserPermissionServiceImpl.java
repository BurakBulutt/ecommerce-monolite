package com.examplesoft.ecommercemonolite.domain.userpermission.service;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.Permission;
import com.examplesoft.ecommercemonolite.domain.permisson.service.PermissionService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.NewPermissionCreationEvent;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.UserPermissionDto;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.UserPermissionMapper;
import com.examplesoft.ecommercemonolite.domain.userpermission.entity.UserPermission;
import com.examplesoft.ecommercemonolite.domain.userpermission.repo.UserPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPermissionServiceImpl {
    private final UserPermissionRepository repository;
    private final UserService userService;
    private final PermissionService permissionService;

    public List<UserPermissionDto> getAllByUserId(String userId) {
        return repository.findAllByUserId(userId).stream()
                .map(userPermission -> {
                    PermissionDto permissionDto = permissionService.getById(userPermission.getPermissionId());
                    UserDto userDto = userService.getById(userPermission.getUserId());

                    return UserPermissionMapper.toDto(userPermission, userDto, permissionDto);
                })
                .toList();
    }

    public UserPermissionDto save(UserPermissionDto dto) {
        PermissionDto permissionDto = permissionService.getById(dto.getPermission().getId());
        UserDto userDto = userService.getById(dto.getUser().getId());

        return UserPermissionMapper.toDto(repository.save(UserPermissionMapper.toEntity(new UserPermission(),dto)), userDto, permissionDto);
    }

    @EventListener
    public void newPermissionEvent(NewPermissionCreationEvent event) {
        List<UserDto> allUsers = userService.getAll();

        allUsers.forEach(userDto -> {
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userDto.getId());
            userPermission.setPermissionId(event.permissionId());
            repository.save(userPermission);
        });
    }
}
