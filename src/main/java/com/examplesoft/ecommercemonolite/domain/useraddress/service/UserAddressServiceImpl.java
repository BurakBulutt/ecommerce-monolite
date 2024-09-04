package com.examplesoft.ecommercemonolite.domain.useraddress.service;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.domain.useraddress.dto.UserAddressDto;
import com.examplesoft.ecommercemonolite.domain.useraddress.dto.UserAddressMapper;
import com.examplesoft.ecommercemonolite.domain.useraddress.entity.UserAddress;
import com.examplesoft.ecommercemonolite.domain.useraddress.repo.UserAddressRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepository repository;
    private final UserService userService;

    @Override
    public List<UserAddressDto> getAll() {
        return repository.findAll().stream()
                .map(userAddress -> UserAddressMapper.toDto(userAddress, userService.getById(userAddress.getUserId())))
                .toList();
    }

    @Override
    public List<UserAddressDto> getUserAddresses(String userId) {
        return repository.findAllByUserId(userId).stream()
                .map(userAddress -> UserAddressMapper.toDto(userAddress, userService.getById(userAddress.getUserId())))
                .toList();
    }

    @Override
    public UserAddressDto getById(String id) {
        return repository.findById(id).map(userAddress -> {
                    UserDto user = userService.getById(userAddress.getUserId());
                    return UserAddressMapper.toDto(userAddress, user);
                })
                .orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, UserAddress.class.getSimpleName(), id));
    }

    @Override
    @Transactional
    public UserAddressDto save(UserAddressDto userAddressDto) {
        UserDto user = userService.getById(userAddressDto.getUser().getId());
        return UserAddressMapper.toDto(repository.save(UserAddressMapper.toEntity(new UserAddress(),userAddressDto)), user);
    }

    @Override
    @Transactional
    public UserAddressDto update(String id, UserAddressDto userAddressDto) {
        UserAddress userAddress = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, UserAddress.class.getSimpleName(), id));
        UserDto user = userService.getById(userAddressDto.getUser().getId());
        return UserAddressMapper.toDto(repository.save(UserAddressMapper.toEntity(userAddress,userAddressDto)), user);
    }

    @Override
    @Transactional
    public void delete(String id) {
        UserAddress userAddress = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, UserAddress.class.getSimpleName(), id));
        repository.delete(userAddress);
    }
}
