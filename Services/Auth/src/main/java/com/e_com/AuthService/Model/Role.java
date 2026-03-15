package com.e_com.AuthService.Model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private UUID id;
    private String code;
    private String name;
    
    public com.e_com.AuthService.Entity.Role toEntity() {
        return new com.e_com.AuthService.Entity.Role(code, name);
    }
}
