package com.e_com.VendorService.Vendor.Domain.Model;

import com.e_com.VendorService.Shared.Domain.AggregateRoot;

public class Vendor extends AggregateRoot<Long> {

    private Long userId;
    private String name;
    private VendorStatus status;

    public Vendor(Long id, Long userId, String name) {
        super(id);

        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        this.userId = userId;
        this.name = name;
        this.status = VendorStatus.PENDING;
    }

    public Vendor(Long id, Long userId, String name, VendorStatus status) {
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

        // sẽ add event ở bước sau
        // addDomainEvent(new VendorActivatedEvent(this.id));
    }

    public void ban() {
        this.status = VendorStatus.BANNED;
    }

    // ===== getters =====

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public VendorStatus getStatus() {
        return status;
    }
}