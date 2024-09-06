package com.psychologywebsite.services;

import com.psychologywebsite.dtos.auth.AuthResponseDto;
import com.psychologywebsite.dtos.auth.LoginDto;
import com.psychologywebsite.dtos.auth.SignUpDto;
import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.user.UserDto;

public interface AuthService {
    CommonResponseDto<AuthResponseDto> login(LoginDto loginDto);

    CommonResponseDto<UserDto> register(SignUpDto signUpDto);

    CommonResponseDto<String> logout();
}
