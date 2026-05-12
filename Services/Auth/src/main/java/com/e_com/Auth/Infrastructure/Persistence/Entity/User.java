package com.e_com.Auth.Infrastructure.Persistence.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.e_com.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends JpaEntity {
    @Column(unique = true, columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(200)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(255)", nullable = true)
    private String name;

    @Column(columnDefinition = "varchar(255)", nullable = true)
    private String avatar;

    @Column(unique = true, columnDefinition = "varchar(255)", nullable = true)
    private String phone;

    @Column(columnDefinition = "varchar(20) default 'INACTIVE'", nullable = true)
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    protected User() {
    }

    public User(String email, String password, String status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(String email, String password, String status, String name, String avatar, String phone) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
    }

    public com.e_com.Auth.Domain.Model.User toDomain() {
        return new com.e_com.Auth.Domain.Model.User(
            this.getId(), 
            email, 
            password, 
            name, 
            avatar, 
            phone, 
            status, 
            createdAt, 
            roles != null ? roles.stream().map(Role::toDomain).collect(java.util.stream.Collectors.toSet()) : null
        );
    }
}
