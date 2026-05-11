package com.e_com.Auth.Domain.Model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private UUID id;
    private String code;
    private String name;
    
    public com.e_com.Auth.Infrastructure.Persistence.Entity.Role toEntity() {
        return new com.e_com.Auth.Infrastructure.Persistence.Entity.Role(code, name);
    }
}
