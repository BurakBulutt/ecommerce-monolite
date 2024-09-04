package com.examplesoft.ecommercemonolite.domain.useraddress.service;

import com.examplesoft.ecommercemonolite.domain.useraddress.dto.UserAddressDto;

import java.util.List;

public interface UserAddressService {
    List<UserAddressDto> getAll();
    List<UserAddressDto> getUserAddresses(String userId);
    UserAddressDto getById(String id);
    UserAddressDto save(UserAddressDto userAddressDto);
    UserAddressDto update(String id,UserAddressDto userAddressDto);
    void delete(String id);
}
