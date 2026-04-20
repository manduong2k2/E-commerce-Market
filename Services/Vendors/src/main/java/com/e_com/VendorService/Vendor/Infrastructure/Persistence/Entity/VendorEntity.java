package com.e_com.VendorService.Vendor.Infrastructure.Persistence.Entity;

import com.e_com.VendorService.Vendor.Domain.Model.VendorStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vendors")
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendorStatus status;

    // ===== constructors =====

    public VendorEntity() {}

    public VendorEntity(Long id, Long userId, String name, VendorStatus status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
    }
}