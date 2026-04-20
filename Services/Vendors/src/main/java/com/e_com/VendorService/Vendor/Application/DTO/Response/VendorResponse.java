package com.e_com.VendorService.Vendor.Application.DTO.Response;

import com.e_com.VendorService.Vendor.Domain.Model.Vendor;
import com.e_com.VendorService.Vendor.Domain.Model.VendorStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {
    private Long id;
    private Long userId;
    private String name;
    private VendorStatus status;

    public static VendorResponse from(Vendor vendor) {
        return new VendorResponse(
                vendor.getId(),
                vendor.getUserId(),
                vendor.getName(),
                vendor.getStatus()
        );
    }
}
