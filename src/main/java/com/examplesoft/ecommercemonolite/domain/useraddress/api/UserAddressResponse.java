package com.examplesoft.ecommercemonolite.domain.useraddress.api;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAddressResponse {
    private String id;
    private String title;
    private String address;
    private UserDto user;
}
