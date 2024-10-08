package com.psychologywebsite.services;

import com.psychologywebsite.dtos.common.CommonResponseDto;
import com.psychologywebsite.dtos.common.PaginatedDataDto;
import com.psychologywebsite.dtos.user.AddUserDto;
import com.psychologywebsite.dtos.user.ChangePasswordDto;
import com.psychologywebsite.dtos.user.UserDto;
import com.psychologywebsite.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    CommonResponseDto<UserDto> getUserById(Long id);

    UserEntity getCurrentUser();

    PaginatedDataDto<UserDto> getUserByPage(int page);

    CommonResponseDto<UserDto> createUser(AddUserDto addUserDto);

    CommonResponseDto<String> editUser(Long id, String email, String username, String phone, String address, String age,
                                       String avatarUrl, MultipartFile file) throws IOException;

    CommonResponseDto<String> deleteUser(Long id);

    CommonResponseDto<String> changePassword(Long id, ChangePasswordDto changePasswordDto);

    UserEntity findByEmail(String email);

    Long getCurrentUserId();
}
