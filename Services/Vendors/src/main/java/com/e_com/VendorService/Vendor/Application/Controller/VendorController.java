package com.e_com.VendorService.Vendor.Application.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.VendorService.Shared.Application.Annotation.Auth.Authenticated;
import com.e_com.VendorService.Vendor.Application.Command.CreateVendorCommand;
import com.e_com.VendorService.Vendor.Application.Command.RegisterVendorCommand;
import com.e_com.VendorService.Vendor.Application.Command.UpdateVendorCommand;
import com.e_com.VendorService.Vendor.Application.DTO.Request.CreateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Request.RegisterVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Request.UpdateVendorRequest;
import com.e_com.VendorService.Vendor.Application.DTO.Response.VendorResponse;
import com.e_com.VendorService.Vendor.Application.Mapper.CreateVendorCommandMapper;
import com.e_com.VendorService.Vendor.Application.Mapper.RegisterVendorCommandMapper;
import com.e_com.VendorService.Vendor.Application.Mapper.UpdateVendorCommandMapper;
import com.e_com.VendorService.Vendor.Domain.Contract.IVendorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private IVendorService vendorService;
    @Autowired
    private CreateVendorCommandMapper createMapper;
    @Autowired
    private UpdateVendorCommandMapper updateMapper;
    @Autowired
    private RegisterVendorCommandMapper registerMapper;

    @Authenticated
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<VendorResponse> vendors = vendorService.getAll();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("data", vendors);

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @ModelAttribute CreateVendorRequest request) {
        CreateVendorCommand command = createMapper.toDomain(request);
        VendorResponse vendor = vendorService.create(command);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("data", vendor);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activate(@PathVariable UUID id) {
        vendorService.active(id);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id,
            @Valid @RequestBody UpdateVendorRequest request) {
        UpdateVendorCommand command = updateMapper.toDomain(request);
        vendorService.update(id, command);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getByUser() {
        VendorResponse vendor = vendorService.getByUser();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("data", vendor);

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @PostMapping("/me")
    public ResponseEntity<Map<String, Object>> register(@Valid @ModelAttribute RegisterVendorRequest request) {
        RegisterVendorCommand command = registerMapper.toDomain(request);
        VendorResponse vendor = vendorService.register(command);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("data", vendor);

        return ResponseEntity.ok(response);
    }
}
