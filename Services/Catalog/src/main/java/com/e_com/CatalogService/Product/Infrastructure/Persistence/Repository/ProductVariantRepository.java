package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import com.e_com.CatalogService.Product.Domain.Contract.IProductVariantRepository;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class ProductVariantRepository implements IProductVariantRepository {
    private final ProductVariantJpaRepository jpaRepository;

    public ProductVariantRepository(ProductVariantJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProductVariantEntity save(ProductVariantEntity variant) {
        ProductVariantEntity entity = variant;
        ProductVariantEntity saved = jpaRepository.save(entity);
        return saved;
    }

    @Override
    public List<ProductVariantEntity> findAll(){
        return jpaRepository.findAll();
    }

    @Override
    public Optional<ProductVariantEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ProductVariantEntity> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public ProductVariantEntity update(ProductVariantEntity variant) {
        return jpaRepository.save(variant);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
