package com.e_com.VendorService.Vendor.Infrastructure.Persistence.Entity;

import java.util.UUID;

import com.e_com.VendorService.Shared.Infrastructure.Persistence.JpaEntity;
import com.e_com.VendorService.Vendor.Domain.Model.VendorStatus;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "vendors")
@Data
@EqualsAndHashCode(callSuper = false)
public class VendorEntity extends JpaEntity {
    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendorStatus status;

    public VendorEntity() {}

    public VendorEntity(UUID id, UUID userId, String name, VendorStatus status) {
        this.setId(id);
        this.userId = userId;
        this.name = name;
        this.status = status;
    }
}