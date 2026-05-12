package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;

public interface IProductVariantRepository {
    ProductVariantEntity save(ProductVariantEntity variant);

    List<ProductVariantEntity> findAll();

    Optional<ProductVariantEntity> findById(UUID id);

    List<ProductVariantEntity> findByName(String name);

    ProductVariantEntity update(ProductVariantEntity variant);

    void delete(UUID id);
}
