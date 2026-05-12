package com.e_com.Auth.Infrastructure.Persistence.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.e_com.Shared.Infrastructure.Persistence.JpaEntity;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends JpaEntity {

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    public Role() {}

    public Role(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public com.e_com.Auth.Domain.Model.Role toDomain() {
        return new com.e_com.Auth.Domain.Model.Role(id, code, name);
    }
}