package com.e_com.VendorService.Vendor.Application.DTO.Response;

import java.util.UUID;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;
import com.e_com.VendorService.Vendor.Domain.Model.VendorStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {
    private UUID id;
    private UUID userId;
    private String name;
    private VendorStatus status;

    public VendorResponse(Vendor vendor) {
        this.id = vendor.getId();
        this.userId = vendor.getUserId();
        this.name = vendor.getName();
        this.status = vendor.getStatus();
    }
}
