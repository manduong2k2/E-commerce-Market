package com.e_com.Auth.Infrastructure.Persistence.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.Auth.Infrastructure.Persistence.Entity.Role;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
    public Role findByCode(String code);
}
