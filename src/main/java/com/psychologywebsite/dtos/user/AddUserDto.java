package com.psychologywebsite.dtos.user;

import lombok.Data;

@Data
public class AddUserDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
}
