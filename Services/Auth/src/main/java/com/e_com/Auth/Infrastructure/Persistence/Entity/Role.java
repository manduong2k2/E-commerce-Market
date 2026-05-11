package com.e_com.Auth.Infrastructure.Persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

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

    public java.util.UUID getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
}