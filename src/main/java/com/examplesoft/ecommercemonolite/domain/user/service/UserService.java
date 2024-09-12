package com.examplesoft.ecommercemonolite.domain.user.service;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserDto> getAll(Pageable pageable);

    List<UserDto> getAll();

    UserDto getById(String id);

    UserDto getByUsername(String username);

    UserDto findByUsername(String username);

    UserDto save(UserDto userDto);

    UserDto update(String id,UserDto userDto);

    void delete(String id);
}
