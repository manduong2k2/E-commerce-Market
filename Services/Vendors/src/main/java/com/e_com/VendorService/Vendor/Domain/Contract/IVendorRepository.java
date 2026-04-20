package com.e_com.VendorService.Vendor.Domain.Contract;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;

import java.util.Optional;

public interface IVendorRepository {

    Vendor save(Vendor vendor);

    Optional<Vendor> findById(Long id);

    Optional<Vendor> findByUserId(Long userId);
}
