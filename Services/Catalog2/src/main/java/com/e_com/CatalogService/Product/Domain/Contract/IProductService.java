package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.UUID;

import com.e_com.CatalogService.Product.Application.DTO.Response.ProductResponse;
import com.e_com.CatalogService.Product.Domain.Model.Product;

import java.io.IOException;

public interface IProductService {
    public List<ProductResponse> getAllProducts();

    public ProductResponse createProduct(Product product) throws IOException;

    public ProductResponse getProduct(UUID id);

    public ProductResponse updateProduct(UUID id, Product updated);

    public void deleteProduct(UUID id);
}
