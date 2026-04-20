package com.e_com.VendorService.Vendor.Application.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;
import com.e_com.VendorService.Vendor.Application.Service.VendorService;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    
    @PostMapping
    public VendorResponse create(@RequestBody CreateVendorRequest request) {
        return vendorService.createVendor(request);
    }

    @PostMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        vendorService.activateVendor(id);
    }
}
