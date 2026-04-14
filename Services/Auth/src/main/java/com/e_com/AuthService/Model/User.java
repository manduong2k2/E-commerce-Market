package com.e_com.AuthService.Model;

import java.util.Set;
import java.util.UUID;

import com.e_com.AuthService.Constants.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String password;    
    private String status;
    private String name;
    private String avatar;
    private String phone;
    private Set<Role> roles;
    private LocalDateTime createdAt;

    public User(UUID id, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = UserStatus.DEFAULT;
        this.createdAt = createdAt;
    }

    public User(UUID id, String email, String password, String name, String avatar, String phone, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public com.e_com.AuthService.Entity.User toEntity() {
        return new com.e_com.AuthService.Entity.User(this.email, this.password, this.status, this.name, this.avatar, this.phone);
    }
}