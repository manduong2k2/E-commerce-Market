package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByName(String name);
}
