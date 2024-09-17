package com.examplesoft.ecommercemonolite.domain.permisson.dto;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;

public record UserCreationEvent(
        UserDto user
) {
}
