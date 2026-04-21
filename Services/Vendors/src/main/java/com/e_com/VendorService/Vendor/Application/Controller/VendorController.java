package com.e_com.VendorService.Vendor.Application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.VendorService.Shared.Infrastructure.Annotation.Auth.Authenticated;
import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;
import com.e_com.VendorService.Vendor.Application.Service.VendorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Authenticated
    @GetMapping
    public List<VendorResponse> getAll() {
        return vendorService.getAllVendors();
    }

    @Authenticated
    @PostMapping
    public VendorResponse create(@Valid @RequestBody CreateVendorRequest request) {
        return vendorService.createVendor(request);
    }

    @PostMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        vendorService.activateVendor(id);
    }
}
