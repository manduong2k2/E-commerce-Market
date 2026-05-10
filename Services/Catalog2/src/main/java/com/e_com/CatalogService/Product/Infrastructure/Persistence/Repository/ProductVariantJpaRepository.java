package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductVariantJpaRepository extends JpaRepository<ProductVariantEntity, UUID> {
    List<ProductVariantEntity> findByName(String name);
}
