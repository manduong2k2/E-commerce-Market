package com.e_com.VendorService.Vendor.Application.Mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.e_com.VendorService.Shared.Domain.Contract.IMapper;
import com.e_com.VendorService.Vendor.Application.Command.CreateVendorCommand;
import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;

@Component
public class CreateVendorCommandMapper implements IMapper<CreateVendorCommand, CreateVendorRequest> {

    @Override
    public CreateVendorCommand toDomain(CreateVendorRequest request) {
        return new CreateVendorCommand(
            request.getUserId(),
            request.getName(),
            request.getDescription(),
            request.getLogo(),
            request.getBanner(),
            request.getTaxCode(),
            request.getEmail(),
            request.getAddressId() != null ? UUID.fromString(request.getAddressId()) : null,
            request.getPhone()
        );
    }

    @Override
    public CreateVendorRequest toEntity(CreateVendorCommand command) {
        return new CreateVendorRequest(
            command.getUserId(),
            command.getName(),
            command.getDescription(),
            command.getLogo(),
            command.getBanner(),
            command.getTaxCode(),
            command.getEmail(),
            command.getAddressId() != null ? command.getAddressId().toString() : null,
            command.getPhone()
        );
    }
}
