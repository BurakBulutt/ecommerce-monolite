package com.examplesoft.ecommercemonolite.domain.user.api;


import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toDto(UserRequest request) {
        return UserDto.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .isVerified(request.getIsVerified())
                .isActive(request.getIsActive())
                .build();
    }

    public static UserResponse toResponse(UserDto userDto) {
        return UserResponse.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .isVerified(userDto.getIsVerified())
                .isActive(userDto.getIsActive())
                .userType(userDto.getUserType())
                .build();
    }

    public static Page<UserResponse> toPageResponse(Page<UserDto> userDtos) {
        return PageUtil.toPage(userDtos,UserMapper::toResponse);
    }
}
