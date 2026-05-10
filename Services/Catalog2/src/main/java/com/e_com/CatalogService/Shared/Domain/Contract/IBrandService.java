package com.e_com.CatalogService.Shared.Domain.Contract;

import java.util.List;
import java.util.UUID;

import com.e_com.CatalogService.Brand.Application.DTO.Request.CreateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Request.UpdateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Response.BrandResponse;

public interface IBrandService {
    public List<BrandResponse> getAllBrands();

    public BrandResponse createBrand(CreateBrandRequest request);

    public BrandResponse getBrand(UUID id);

    public BrandResponse updateBrand(UUID id, UpdateBrandRequest request);

    public void deleteBrand(UUID id);

}
