package com.psychologywebsite.controllers;

import com.psychologywebsite.dtos.auth.AuthResponseDto;
import com.psychologywebsite.dtos.auth.LoginDto;
import com.psychologywebsite.dtos.auth.SignUpDto;
import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.user.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/register")
    CommonResponseDto<UserDto> register(@RequestBody SignUpDto signUpDto);

    @PostMapping("/login")
    CommonResponseDto<AuthResponseDto> login(@RequestBody LoginDto loginDto);

    @PostMapping("/logout")
    CommonResponseDto<String> logout();
}
