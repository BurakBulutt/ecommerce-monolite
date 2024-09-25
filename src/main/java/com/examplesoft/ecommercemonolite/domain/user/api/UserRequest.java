package com.examplesoft.ecommercemonolite.domain.user.api;

import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private Boolean isActive = Boolean.TRUE;
    private Boolean isVerified = Boolean.TRUE;
    private UserType userType = UserType.USER;
}
