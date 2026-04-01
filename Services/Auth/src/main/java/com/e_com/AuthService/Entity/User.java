package com.e_com.AuthService.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@SoftDelete(columnName = "deleted_at", strategy = SoftDeleteType.TIMESTAMP)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(200)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(20) default 'ACTIVE'", nullable = true)
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    protected User() {
    }

    public User(String email, String password, String status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public com.e_com.AuthService.Model.User toDomain() {
        return new com.e_com.AuthService.Model.User(id, email, password, status, roles.stream().map(Role::toDomain).collect(java.util.stream.Collectors.toSet()), createdAt);
    }
}
