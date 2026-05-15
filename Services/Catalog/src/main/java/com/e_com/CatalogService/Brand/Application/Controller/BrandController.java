package com.e_com.CatalogService.Brand.Application.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.CatalogService.Brand.Application.DTO.Request.CreateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Request.UpdateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Response.BrandResponse;
import com.e_com.CatalogService.Brand.Domain.Contract.IBrandService;
import com.e_com.CatalogService.Shared.Application.Annotation.Auth.Authenticated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAll() {
        List<BrandResponse> brands = brandService.getAllBrands();

        HashMap<String,Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", brands);
        
        return ResponseEntity.ok().body(response);
    }

    @Authenticated
    @PostMapping
    public BrandResponse create(@Valid @ModelAttribute CreateBrandRequest request) {
        return brandService.createBrand(request);
    }

    @Authenticated
    @GetMapping("/{brandId}")
    public BrandResponse details(@PathVariable UUID brandId) {
        return brandService.getBrand(brandId);
    }
    
    @Authenticated
    @PutMapping("/{brandId}")
    public BrandResponse update(@PathVariable UUID brandId, @Valid @ModelAttribute UpdateBrandRequest request) {
        return brandService.updateBrand(brandId, request);
    }
    
    @Authenticated
    @DeleteMapping("/{brandId}")
    public void delete(@PathVariable UUID brandId) {
        brandService.deleteBrand(brandId);
    }
}