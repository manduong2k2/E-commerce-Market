package com.e_com.VendorService.Vendor.Domain.Contract;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;

import java.util.List;
import java.util.Optional;

public interface IVendorRepository {
    Vendor save(Vendor vendor);

    List<Vendor> findAll();

    Optional<Vendor> findById(Long id);

    Optional<Vendor> findByUserId(Long userId);
}
