package com.examplesoft.ecommercemonolite.domain.user.service.impl;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.UserCreationEvent;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserMapper;
import com.examplesoft.ecommercemonolite.domain.user.entity.User;
import com.examplesoft.ecommercemonolite.domain.user.repo.UserRepository;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable), UserMapper::toDto);
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDto getById(String id) {
        return repository.findById(id).map(UserMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),id));
    }

    @Override
    public UserDto getByUsername(String username) {
        return repository.findByUsername(username).map(UserMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),username));
    }

    @Override
    public UserDto findByUsername(String username) {
        return repository.findByUsername(username).map(UserMapper::toDto).orElse(null);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        UserDto user = UserMapper.toDto(repository.save(UserMapper.toEntity(new User(), userDto)));
        publisher.publishEvent(new UserCreationEvent(user));
        return user;
    }

    @Override
    @Transactional
    public UserDto update(String id, UserDto userDto) {
        User user = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),id));
        return UserMapper.toDto(repository.save(UserMapper.toEntity(user, userDto)));
    }

    @Override
    @Transactional
    public void delete(String id) {
        User user = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),id));
        repository.delete(user);
    }
}
