package com.e_com.AuthService.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.AuthService.Entity.Role;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
    public Role findByCode(String code);
}
