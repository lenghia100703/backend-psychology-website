package com.psychologywebsite.services.impl;

import com.psychologywebsite.dtos.auth.AuthResponseDto;
import com.psychologywebsite.dtos.auth.LoginDto;
import com.psychologywebsite.dtos.auth.SignUpDto;
import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.user.UserDto;
import com.psychologywebsite.dtos.user.UserInfoInToken;
import com.psychologywebsite.entities.UserEntity;
import com.psychologywebsite.enums.AuthProvider;
import com.psychologywebsite.enums.ResponseCode;
import com.psychologywebsite.enums.Role;
import com.psychologywebsite.exceptions.CommonException;
import com.psychologywebsite.repositories.UserRepository;
import com.psychologywebsite.securities.JWTProvider;
import com.psychologywebsite.services.AuthService;
import com.psychologywebsite.utils.SecurityContextUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${default.avatar}")
    String defaultAvatar;

    private final HttpServletResponse response;

    public AuthServiceImpl(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public CommonResponseDto<AuthResponseDto> login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        Optional<UserEntity> optionalUser = userRepository.findUserByEmail(loginDto.getEmail());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            String accessToken = jwtProvider.generateAccessToken(response, new UserInfoInToken(user.getId()), String.valueOf(user.getRole()));
            String refreshToken = jwtProvider.generateRefreshToken(response, new UserInfoInToken(user.getId()), String.valueOf(user.getRole()));
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            AuthResponseDto authResponse = new AuthResponseDto(user.getId(), accessToken, refreshToken);
            userRepository.save(user);
            return new CommonResponseDto<>(authResponse);
        } else {
            return new CommonResponseDto<>(ResponseCode.ERROR);
        }
    }

    @Override
    public CommonResponseDto<UserDto> register(SignUpDto signUpDto) {
        if (userRepository.findUserByEmail(signUpDto.getEmail()).isPresent()) {
            throw new CommonException(ResponseCode.ERROR, "Email đã tồn tại");
        }

        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setRole(Role.valueOf(signUpDto.getRole()));
        user.setProvider(AuthProvider.LOCAL);
        user.setAvatar(defaultAvatar);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setCreatedBy(signUpDto.getEmail());
        userRepository.save(user);

        return new CommonResponseDto<>(new UserDto(userRepository.save(user)));
    }

    @Override
    public CommonResponseDto<String> logout() {
        Long id = SecurityContextUtil.getCurrentUserId();
        UserEntity currentUser = userRepository.findById(id).get();
        currentUser.setAccessToken(null);
        currentUser.setRefreshToken(null);
        userRepository.save(currentUser);
        SecurityContextHolder.clearContext();

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", null)
                .maxAge(1000)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        ResponseCookie jwtRefreshCookie = ResponseCookie.from("jwt-refresh", null)
                .maxAge(1000)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString());

        return new CommonResponseDto<>("Logged out successfully");
    }
}
