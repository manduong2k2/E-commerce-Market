package com.e_com.CatalogService.Product.Infrastructure.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ExtraAttributeEntity;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

@Component
public class ProductVariantMapper implements IMapper<ProductVariant, ProductVariantEntity>{
    @Autowired
    private IMapper<ExtraAttribute, ExtraAttributeEntity> extraAttributeMapper;

    @Override
    public ProductVariant toDomain(ProductVariantEntity entity) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(entity.getId());
        productVariant.setPrice(entity.getPrice());
        productVariant.setCode(entity.getCode());
        productVariant.setName(entity.getName());
        productVariant.setExtraAttributes(
            entity.getExtraAttributes() != null ? 
            entity.getExtraAttributes().stream().map(extraAttributeMapper::toDomain).collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        return productVariant;
    }
    
    @Override
    public ProductVariantEntity toEntity(ProductVariant domain) {
        ProductVariantEntity entity = new ProductVariantEntity();
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setPrice(domain.getPrice().getValue());
        entity.setExtraAttributes(
            domain.getExtraAttributes() != null ? 
            domain.getExtraAttributes().stream()
            .map(extraAttributeMapper::toEntity)
            .peek(extraAttribute -> extraAttribute.setVariant(entity))
            .collect(java.util.stream.Collectors.toList()) : 
            java.util.Collections.emptyList()
        );
        return entity;
    }
}
