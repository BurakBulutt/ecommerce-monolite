package com.examplesoft.ecommercemonolite.domain.auth.service.impl;

import com.examplesoft.ecommercemonolite.domain.auth.api.LoginRequest;
import com.examplesoft.ecommercemonolite.domain.auth.api.RegisterRequest;
import com.examplesoft.ecommercemonolite.domain.auth.dto.Token;
import com.examplesoft.ecommercemonolite.domain.auth.service.AuthService;
import com.examplesoft.ecommercemonolite.domain.basket.service.UserBasketCreationEvent;
import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.dto.UserCreationEvent;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import com.examplesoft.ecommercemonolite.domain.permisson.service.PermissionService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.domain.userpermission.dto.UserPermissionDto;
import com.examplesoft.ecommercemonolite.domain.userpermission.service.UserPermissionServiceImpl;
import com.examplesoft.ecommercemonolite.security.CustomUserDetails;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.security.UserDetailsServiceImpl;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PermissionService permissionService;
    private final UserPermissionServiceImpl userPermissionService;
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        if (userService.findByUsername(request.username()) != null) {
            throw new BaseException(MessageUtil.ENTITY_ALREADY_EXISTS);
        }

        UserDto user = userService.save(UserDto.builder()
                .name(request.name())
                .surname(request.surname())
                .username(request.username())
                .password(encoder.encode(request.password()))
                .userType(UserType.USER)
                .isActive(Boolean.TRUE)
                .isVerified(Boolean.TRUE)
                .build());

        publisher.publishEvent(new UserBasketCreationEvent(user.getId()));
    }

    @Override
    @Transactional
    public Token login(LoginRequest request, UserType userType) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        if (!encoder.matches(request.password(), userDetails.getPassword())) {
            throw new BaseException(MessageUtil.USERNAME_OR_PASSWORD_WRONG);
        }

        List<PermissionDto> permissions = permissionService.getByPermissionType(getType(userType));

        Set<String> userPermissionIds = userPermissionService.getAllByUserId(userDetails.getId()).stream()
                .map(up -> up.getPermission().getId())
                .collect(Collectors.toSet());

        permissions = permissions.stream().filter(permissionDto -> userPermissionIds.contains(permissionDto.getId())).toList();

        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(permissionDto -> new SimpleGrantedAuthority(permissionDto.getName()))
                .toList();

        return new Token(jwtUtil.generateToken(userDetails, authorities));
    }

    public PermissionType getType(UserType userType) {
        switch (userType) {
            case USER -> {
                return PermissionType.USER;
            }
            case ADMIN -> {
                return PermissionType.ADMIN;
            }
            default -> throw new BaseException(MessageUtil.FAIL);
        }
    }
}
