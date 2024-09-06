package com.psychologywebsite.controllers.impl;

import com.psychologywebsite.controllers.AuthController;
import com.psychologywebsite.dtos.auth.AuthResponseDto;
import com.psychologywebsite.dtos.auth.LoginDto;
import com.psychologywebsite.dtos.auth.SignUpDto;
import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.user.UserDto;
import com.psychologywebsite.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Autowired
    AuthService authService;

    @Override
    public CommonResponseDto<UserDto> register(SignUpDto signUpDto) {
        return authService.register(signUpDto);
    }

    @Override
    public CommonResponseDto<AuthResponseDto> login(LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @Override
    public CommonResponseDto<String> logout() {
        return authService.logout();
    }
}
