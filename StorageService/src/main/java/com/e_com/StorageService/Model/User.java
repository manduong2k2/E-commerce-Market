package com.e_com.StorageService.Model;

import java.util.Set;
import java.util.UUID;

import com.e_com.StorageService.Constants.UserStatus;

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
    private Set<String> roles;
    private LocalDateTime createdAt;

    public User(String email, String password, String status, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public User(UUID id, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = UserStatus.DEFAULT;
        this.createdAt = createdAt;
    }
}