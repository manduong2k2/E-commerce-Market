package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;

public interface IProductRepository {
    ProductEntity save(ProductEntity Product);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(UUID id);

    List<ProductEntity> findByName(String name);

    ProductEntity update(ProductEntity Product);

    void delete(UUID id);
}
