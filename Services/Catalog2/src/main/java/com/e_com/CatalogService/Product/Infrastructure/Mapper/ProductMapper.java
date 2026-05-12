package com.e_com.CatalogService.Product.Infrastructure.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Brand.Infrastructure.Persistence.Entity.BrandEntity;
import com.e_com.CatalogService.Category.Infrastructure.Persistence.Entity.CategoryEntity;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Domain.Model.ProductStatus;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

@Component
public class ProductMapper implements IMapper<Product, ProductEntity> {
    @Autowired
    private IMapper<ProductVariant, ProductVariantEntity> variantMapper;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Product toDomain(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setCode(entity.getCode());
        product.setBrandId(entity.getBrand().getId());
        product.setStatus(new ProductStatus(entity.getStatus()));
        product.setVariants(
            entity.getVariants() != null ? 
            entity.getVariants().stream().map(variantMapper::toDomain).collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        product.setCategoryIds(
            entity.getCategories() != null ? 
            entity.getCategories().stream().map(category -> category.getId()).collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        return product;
    }
    
    public ProductEntity toEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setBrand(entityManager.find(BrandEntity.class, domain.getBrandId()));
        entity.setCategories(
            domain.getCategoryIds() != null ? 
            domain.getCategoryIds().stream().map(categoryId -> entityManager.find(CategoryEntity.class, categoryId)).collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        entity.setStatus(domain.getStatus());
        entity.setCode(domain.getCode());
        entity.setVariants(
            domain.getVariants() != null ? 
            domain.getVariants().stream().map(variantMapper::toEntity)
            .peek(variant -> variant.setProduct(entity))
            .collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        return entity;
    }
}
