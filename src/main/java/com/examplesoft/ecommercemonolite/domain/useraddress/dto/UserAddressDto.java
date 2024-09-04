package com.examplesoft.ecommercemonolite.domain.useraddress.dto;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAddressDto {
    private String id;
    private String title;
    private String address;
    private UserDto user;
}
