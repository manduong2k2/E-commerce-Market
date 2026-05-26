package com.e_com.StorageService.Response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.e_com.StorageService.Entity.File;
import com.e_com.StorageService.Model.User;
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
    private Set<File> roles;
    private LocalDateTime createdAt;

    public ProfileResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }
}
