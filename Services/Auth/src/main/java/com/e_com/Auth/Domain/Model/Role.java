package com.e_com.Auth.Domain.Model;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.e_com.Shared.Domain.AggregateRoot;

@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends AggregateRoot<UUID> {
    private String code;
    private String name;

    public Role(UUID id, String code, String name) {
        super(id);
        this.code = code;
        this.name = name;
    }
    
    public com.e_com.Auth.Infrastructure.Persistence.Entity.Role toEntity() {
        return new com.e_com.Auth.Infrastructure.Persistence.Entity.Role(code, name);
    }
}
