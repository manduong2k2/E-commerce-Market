package com.e_com.CatalogService.Product.Application.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Product.Domain.Contract.IProductRepository;
import com.e_com.CatalogService.Product.Domain.Contract.IProductVariantMapper;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;

@Component
public class ProductVariantMapper implements IProductVariantMapper{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public ProductVariant toDomain(ProductVariantEntity entity) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(entity.getId());
        productVariant.setProductId(entity.getProduct().getId());
        productVariant.setPrice(entity.getPrice());
        productVariant.setExtraAttributes(entity.getExtraAttributes().stream().map(ExtraAttributeMapper::toDomain).collect(java.util.stream.Collectors.toList()));
        return productVariant;
    }
    
    @Override
    public ProductVariantEntity toEntity(ProductVariant domain) {
        ProductVariantEntity entity = new ProductVariantEntity();
        entity.setId(domain.getId());
        entity.setProduct(productRepository.findById(domain.getProductId()).get());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setPrice(domain.getPrice().getValue());
        entity.setExtraAttributes(domain.getExtraAttributes().stream().map(ExtraAttributeMapper::toEntity).collect(java.util.stream.Collectors.toList()));
        return entity;
    }
}
