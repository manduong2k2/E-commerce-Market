package com.e_com.CatalogService.Shared.Domain.Auth;

import java.util.Set;
import java.util.UUID;

import com.e_com.CatalogService.Shared.Domain.Entity;

public class User extends Entity<UUID>{
    private UUID id;
    private Set<String> roles;

    public User(UUID id) {
        super(id);
    }

    public UUID getUserId() {
        return id;
    }
    
    public Set<String> getRoles() {
        return roles;
    }
}
