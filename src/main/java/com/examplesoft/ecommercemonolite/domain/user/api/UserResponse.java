package com.examplesoft.ecommercemonolite.domain.user.api;

import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserResponse {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private Boolean isActive;
    private Boolean isVerified;
    private UserType userType;
}
