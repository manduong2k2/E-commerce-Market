package com.e_com.VendorService.Vendor.Infrastructure.Persistence.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.e_com.VendorService.Vendor.Infrastructure.Persistence.Entity.VendorEntity;

public interface VendorJpaRepository extends JpaRepository<VendorEntity, Long> {
    Optional<VendorEntity> findByUserId(Long userId);
}
