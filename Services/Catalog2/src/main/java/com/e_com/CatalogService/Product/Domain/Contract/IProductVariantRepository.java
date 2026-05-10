package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;

public interface IProductVariantRepository {
    ProductVariant save(ProductVariant variant);

    List<ProductVariant> findAll();

    Optional<ProductVariant> findById(UUID id);

    List<ProductVariant> findByName(String name);

    ProductVariant update(ProductVariant variant);

    void delete(UUID id);
}
