package com.e_com.VendorService.Vendor.Application.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.e_com.VendorService.Shared.Infrastructure.EventPublisher;
import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;
import com.e_com.VendorService.Vendor.Domain.Contract.IVendorRepository;
import com.e_com.VendorService.Vendor.Domain.Model.Vendor;

import jakarta.transaction.Transactional;

public class VendorService {
    @Autowired
    public IVendorRepository vendorRepository;
    @Autowired
    public EventPublisher eventPublisher; 

    @Transactional
    public VendorResponse createVendor(CreateVendorRequest request) {

        // 1. build domain
        Vendor vendor = new Vendor(
                null,
                request.getUserId(),
                request.getName()
        );

        // 2. persist
        vendor = vendorRepository.save(vendor);

        // 3. publish domain events (nếu có)
        publishDomainEvents(vendor);

        // 4. return response
        return VendorResponse.from(vendor);
    }

    @Transactional
    public void activateVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.activate();

        vendor = vendorRepository.save(vendor);

        publishDomainEvents(vendor);
    }

    private void publishDomainEvents(Vendor vendor) {
        vendor.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, java.util.Optional.empty()));

        vendor.clearDomainEvents();
    }
}
