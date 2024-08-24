package com.examplesoft.ecommercemonolite.domain.user.api;

import lombok.Data;

@Data
public class UserRequest {
    private final String name;
    private final String surname;
    private final String username;
    private final String password;
    private final String email;
    private final Boolean isActive = Boolean.TRUE;
    private final Boolean isVerified = Boolean.TRUE;
}
