package com.e_com.VendorService.Vendor.Infrastructure.Persistence.Repository;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;
import com.e_com.VendorService.Vendor.Domain.Contract.IVendorRepository;
import com.e_com.VendorService.Vendor.Infrastructure.Persistence.Entity.VendorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VendorRepository implements IVendorRepository {

    private final VendorJpaRepository jpaRepository;

    public VendorRepository(VendorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Vendor> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Vendor save(Vendor vendor) {
        VendorEntity entity = toEntity(vendor);
        VendorEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Vendor> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<Vendor> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(this::toDomain);
    }

    // ===== mapping =====

    private Vendor toDomain(VendorEntity entity) {
        return new Vendor(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getStatus()
        );
    }

    private VendorEntity toEntity(Vendor vendor) {
        return new VendorEntity(
                vendor.getId(),
                vendor.getUserId(),
                vendor.getName(),
                vendor.getStatus()
        );
    }
}