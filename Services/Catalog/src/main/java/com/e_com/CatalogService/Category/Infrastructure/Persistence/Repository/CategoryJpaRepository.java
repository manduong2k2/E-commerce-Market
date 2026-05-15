package com.e_com.CatalogService.Category.Infrastructure.Persistence.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.CatalogService.Category.Infrastructure.Persistence.Entity.CategoryEntity;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByName(String name);
}
