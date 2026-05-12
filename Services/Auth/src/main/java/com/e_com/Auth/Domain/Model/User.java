package com.e_com.Auth.Domain.Model;

import java.util.Set;
import java.util.UUID;

import com.e_com.Auth.Domain.Constants.UserStatus;
import com.e_com.Shared.Domain.AggregateRoot;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AggregateRoot<UUID>{
    private String email;
    private String password;    
    private String status;
    private String name;
    private String avatar;
    private String phone;
    private Set<Role> roles;
    private LocalDateTime createdAt;

    public User() {
        super(null);
    }

    public User(UUID id, String email, String password, LocalDateTime createdAt) {
        super(id);
        this.email = email;
        this.password = password;
        this.status = UserStatus.DEFAULT;
        this.createdAt = createdAt;
    }

    public User(UUID id, String email, String password, String name, String avatar, String phone, String status, LocalDateTime createdAt, Set<Role> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
        this.status = status;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public com.e_com.Auth.Infrastructure.Persistence.Entity.User toEntity() {
        return new com.e_com.Auth.Infrastructure.Persistence.Entity.User(this.email, this.password, this.status, this.name, this.avatar, this.phone);
    }
}