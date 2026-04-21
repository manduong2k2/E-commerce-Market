package com.e_com.VendorService.Vendor.Domain.Model;

import java.util.UUID;

import com.e_com.VendorService.Shared.Domain.AggregateRoot;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Vendor extends AggregateRoot<UUID> {
    private UUID userId;
    private String name;
    private VendorStatus status;

    public Vendor(UUID id, UUID userId, String name) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.status = VendorStatus.PENDING;
    }

    public Vendor(UUID id, UUID userId, String name, VendorStatus status) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.status = status;
    }

    public void activate() {
        if (this.status != VendorStatus.PENDING) {
            throw new IllegalStateException("Vendor must be PENDING to activate");
        }

        this.status = VendorStatus.ACTIVE;
    }

    public void ban() {
        this.status = VendorStatus.BANNED;
    }
}