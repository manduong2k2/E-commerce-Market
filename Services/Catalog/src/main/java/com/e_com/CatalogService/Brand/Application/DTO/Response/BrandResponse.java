package com.e_com.CatalogService.Brand.Application.DTO.Response;

import java.util.UUID;

import com.e_com.CatalogService.Brand.Domain.Model.Brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private UUID id;
    private String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
