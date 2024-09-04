package com.examplesoft.ecommercemonolite.domain.useraddress.api;

import com.examplesoft.ecommercemonolite.domain.user.api.UserResponse;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.useraddress.dto.UserAddressDto;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAddressMapper {

    public static UserAddressDto toDto(UserAddressRequest request){
        return UserAddressDto.builder()
                .title(request.title())
                .address(request.address())
                .user(UserDto.builder().id(request.userId()).build())
                .build();
    }

    public static UserAddressDto toDto(UserAddressUserRequest request){
        return UserAddressDto.builder()
                .title(request.title())
                .address(request.address())
                .user(UserDto.builder().id(JwtUtil.extractUserId()).build())
                .build();
    }

    public static UserAddressResponse toResponse(UserAddressDto dto){
        return UserAddressResponse.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .address(dto.getAddress())
                .user(dto.getUser())
                .build();
    }

    public static List<UserAddressResponse> toDataResponse(List<UserAddressDto> dtos){
        return dtos.stream().map(UserAddressMapper::toResponse).toList();
    }
}
