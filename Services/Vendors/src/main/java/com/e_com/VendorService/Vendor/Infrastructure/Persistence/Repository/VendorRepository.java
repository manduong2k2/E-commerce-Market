package com.e_com.VendorService.Vendor.Infrastructure.Persistence.Repository;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;
import com.e_com.VendorService.Shared.Domain.Contract.IMapper;
import com.e_com.VendorService.Vendor.Domain.Contract.IVendorRepository;
import com.e_com.VendorService.Vendor.Infrastructure.Persistence.Entity.VendorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VendorRepository implements IVendorRepository {

    private final VendorJpaRepository jpaRepository;

    private IMapper<Vendor, VendorEntity> vendorMapper;

    public VendorRepository(VendorJpaRepository jpaRepository, IMapper<Vendor, VendorEntity> vendorMapper) {
        this.jpaRepository = jpaRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<Vendor> findAll() {
        return jpaRepository.findAll().stream()
                .map(vendorMapper::toDomain)
                .toList();
    }

    @Override
    public Vendor save(Vendor vendor) {
        VendorEntity entity = vendorMapper.toEntity(vendor);
        VendorEntity saved = jpaRepository.save(entity);
        return vendorMapper.toDomain(saved);
    }

    @Override
    public Optional<Vendor> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(vendorMapper::toDomain);
    }

    @Override
    public Optional<Vendor> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(vendorMapper::toDomain);
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).isEmpty()
            ? false : true;
    }
}