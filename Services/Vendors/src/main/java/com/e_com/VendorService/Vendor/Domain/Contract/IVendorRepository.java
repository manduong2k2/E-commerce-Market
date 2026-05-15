package com.e_com.VendorService.Vendor.Domain.Contract;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IVendorRepository {
    Vendor save(Vendor vendor);

    List<Vendor> findAll();

    Optional<Vendor> findById(UUID id);

    Optional<Vendor> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
