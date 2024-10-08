package com.psychologywebsite.dtos.user;

import com.psychologywebsite.entities.UserEntity;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private String avatar;
    private String address;
    private String age;
    private String provider;
    private String providerId;
    private Boolean emailConfirmed;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public UserDto(String avatar, String email, String username, Long id) {
        this.avatar = avatar;
        this.email = email;
        this.username = username;
        this.id = id;
    }

    public UserDto(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.role = user.getRole().toString();
        this.avatar = user.getAvatar();
        this.age = user.getAge();
        this.provider = user.getProvider().toString();
        this.providerId = user.getProviderId();
        this.emailConfirmed = user.getEmailConfirmed();
        this.active = user.getActive();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.createdBy = user.getCreatedBy();
        this.updatedBy = user.getUpdatedBy();
    }
}

