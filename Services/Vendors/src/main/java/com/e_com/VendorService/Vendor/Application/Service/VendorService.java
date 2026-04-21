package com.e_com.VendorService.Vendor.Application.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.VendorService.Shared.Infrastructure.IRabbitMQEventPublisher;
import com.e_com.VendorService.Shared.Infrastructure.Utils.Auth.ContextHolder;
import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;
import com.e_com.VendorService.Vendor.Domain.Contract.IVendorRepository;
import com.e_com.VendorService.Vendor.Domain.Model.Vendor;

import jakarta.transaction.Transactional;

@Service
public class VendorService {
    @Autowired
    public IVendorRepository vendorRepository;
    @Autowired
    public IRabbitMQEventPublisher eventPublisher; 

    public List<VendorResponse> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(VendorResponse::new)
                .toList();
    }

    @Transactional
    public VendorResponse createVendor(CreateVendorRequest request) {

        UUID userId = request.getUserId() != null ? request.getUserId() : ContextHolder.getUser().getId();

        Vendor vendor = new Vendor(
                null,
                userId,
                request.getName()
        );

        vendor = vendorRepository.save(vendor);

        publishDomainEvents(vendor, "vendor.created");

        return new VendorResponse(vendor);
    }

    @Transactional
    public void activateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.activate();

        vendor = vendorRepository.save(vendor);

        publishDomainEvents(vendor, "vendor.activated");
    }

    @Transactional
    public void banVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.ban();

        vendor = vendorRepository.save(vendor);

        publishDomainEvents(vendor, "vendor.banned");
    }

    @Transactional
    public void updateVendor(Long vendorId, String name) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setName(name);

        vendor = vendorRepository.save(vendor);

        publishDomainEvents(vendor, "vendor.updated");
    }

    @Async
    private void publishDomainEvents(Vendor vendor, String queue) {
        vendor.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, queue));

        vendor.clearDomainEvents();
    }
}
