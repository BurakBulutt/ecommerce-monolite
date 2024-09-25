package com.examplesoft.ecommercemonolite.domain.permisson.service;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionMapper;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.Permission;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import com.examplesoft.ecommercemonolite.domain.permisson.repo.PermissionRepository;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.NewPermissionCreationEvent;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<PermissionDto> getByPermissionType(PermissionType permissionType) {
        return repository.findAllByPermissionType(permissionType).stream()
                .map(PermissionMapper::toDto)
                .toList();
    }

    @Override
    public List<PermissionDto> getPermissionByIdIn(Set<String> ids) {
        return repository.findAllById(ids).stream()
                .map(PermissionMapper::toDto)
                .toList();
    }

    @Override
    public PermissionDto getById(String id) {
        return repository.findById(id).map(PermissionMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_ALREADY_EXISTS,Permission.class.getSimpleName(),id));
    }

    @Override
    public PermissionDto save(PermissionDto permissionDto) {
        PermissionDto permission = PermissionMapper.toDto(repository.save(PermissionMapper.toEntity(new Permission(), permissionDto)));
        publisher.publishEvent(new NewPermissionCreationEvent(permission.getId()));
        return permission;
    }

    @Transactional
    public void saveAll(List<PermissionDto> permissions) {
        permissions.forEach(permissionDto -> {
            if (repository.findByName(permissionDto.getName()).isEmpty()) {
                save(permissionDto);
            }
        });
    }
}
