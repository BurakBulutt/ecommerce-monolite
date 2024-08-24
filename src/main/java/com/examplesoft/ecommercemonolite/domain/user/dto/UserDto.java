package com.examplesoft.ecommercemonolite.domain.user.dto;

import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
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
