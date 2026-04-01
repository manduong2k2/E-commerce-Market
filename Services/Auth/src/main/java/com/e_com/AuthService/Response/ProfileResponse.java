package com.e_com.AuthService.Response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.e_com.AuthService.Model.Role;
import com.e_com.AuthService.Model.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"id", "email", "status", "role", "createdAt" })
public class ProfileResponse {
    private UUID id;
    private String email;
    private String status;
    private Set<Role> roles;
    private LocalDateTime createdAt;

    public ProfileResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.roles = user.getRoles();
        this.createdAt = user.getCreatedAt();
    }
}
