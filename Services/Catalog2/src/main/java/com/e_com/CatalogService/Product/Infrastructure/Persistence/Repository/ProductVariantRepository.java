package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import com.e_com.CatalogService.Product.Domain.Contract.IProductVariantRepository;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
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
    public ProductVariant save(ProductVariant variant) {
        ProductVariantEntity entity = toEntity(variant);
        ProductVariantEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<ProductVariant> findAll(){
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<ProductVariant> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ProductVariant> findByName(String name) {
        return jpaRepository.findByName(name).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public ProductVariant update(ProductVariant variant) {
        ProductVariantEntity entity = toEntity(variant);
        ProductVariantEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    private ProductVariant toDomain(ProductVariantEntity entity) {
        ProductVariant variant = new ProductVariant();
        variant.setName(entity.getName());
        variant.setCode(entity.getCode());
        variant.setPrice(entity.getPrice());
        return variant;
    }

    private ProductVariantEntity toEntity(ProductVariant variant) {
        ProductVariantEntity entity = new ProductVariantEntity();
        entity.setName(variant.getName());
        entity.setCode(variant.getCode());
        entity.setPrice(variant.getPrice().getValue());
        return entity;
    }
}
