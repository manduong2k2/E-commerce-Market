package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.UUID;

import com.e_com.CatalogService.Product.Application.DTO.Request.CreateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Request.UpdateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Response.ProductResponse;

import java.io.IOException;

public interface IProductService {
    public List<ProductResponse> getAllProducts();

    public ProductResponse createProduct(CreateProductRequest request) throws IOException;

    public ProductResponse getProduct(UUID id);

    public ProductResponse updateProduct(UUID id, UpdateProductRequest request);

    public void deleteProduct(UUID id);
}
