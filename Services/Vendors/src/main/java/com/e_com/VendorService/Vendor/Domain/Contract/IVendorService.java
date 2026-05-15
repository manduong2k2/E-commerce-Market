package com.e_com.VendorService.Vendor.Domain.Contract;

import java.util.List;
import java.util.UUID;

import com.e_com.VendorService.Vendor.Application.Command.CreateVendorCommand;
import com.e_com.VendorService.Vendor.Application.Command.RegisterVendorCommand;
import com.e_com.VendorService.Vendor.Application.Command.UpdateVendorCommand;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;

public interface IVendorService {
    List<VendorResponse> getAll();

    VendorResponse create(CreateVendorCommand command);

    VendorResponse register(RegisterVendorCommand command);

    void update(UUID vendorId, UpdateVendorCommand command);

    void active(UUID vendorId);

    void ban(UUID vendorId);

    VendorResponse getByUser();
}
