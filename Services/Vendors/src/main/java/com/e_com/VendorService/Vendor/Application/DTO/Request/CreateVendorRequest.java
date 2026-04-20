package com.e_com.VendorService.Vendor.Application.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVendorRequest {
    private Long userId;
    private String name;
}
