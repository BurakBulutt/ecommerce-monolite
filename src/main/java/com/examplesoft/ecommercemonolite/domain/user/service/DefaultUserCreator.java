package com.examplesoft.ecommercemonolite.domain.user.service;

import com.examplesoft.ecommercemonolite.domain.basket.service.UserBasketCreationEvent;
import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import com.examplesoft.ecommercemonolite.domain.permisson.service.PermissionService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserMapper;
import com.examplesoft.ecommercemonolite.domain.user.entity.User;
import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import com.examplesoft.ecommercemonolite.domain.user.repo.UserRepository;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.UserPermissionDto;
import com.examplesoft.ecommercemonolite.domain.userpermission.service.UserPermissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DefaultUserCreator {
    private final UserRepository repository;
    private final PermissionService permissionService;
    private final UserPermissionServiceImpl userPermissionService;
    private final PasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;

    @EventListener(value = ApplicationReadyEvent.class)
    @Order(2)
    @Transactional
    public void createAdmin() {
        if (repository.findByUsername("admin").isEmpty()) {
            UserDto dto = UserDto.builder()
                    .name("admin")
                    .surname("admin")
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .userType(UserType.ADMIN)
                    .isActive(Boolean.TRUE)
                    .isVerified(Boolean.TRUE)
                    .build();

            UserDto user = UserMapper.toDto(repository.save(UserMapper.toEntity(new User(),dto)));

            List<PermissionDto> permissions = permissionService.getByPermissionType(PermissionType.ADMIN);

            permissions.forEach(permission -> {
                userPermissionService.save(UserPermissionDto.builder()
                        .user(user)
                        .permission(permission)
                        .build());
            });

            publisher.publishEvent(new UserBasketCreationEvent(user.getId()));
        }
    }
}
