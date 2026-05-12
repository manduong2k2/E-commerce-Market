package com.e_com.CatalogService.Product.Application.Mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.e_com.CatalogService.Product.Domain.Contract.IProductMapper;
import com.e_com.CatalogService.Product.Domain.Contract.IProductVariantMapper;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Domain.Model.ProductStatus;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;

public class ProductMapper implements IProductMapper {
    @Autowired
    private IProductVariantMapper variantMapper;
    
    @Override
    public Product toDomain(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setCode(entity.getCode());
        product.setBrandId(entity.getBrand().getId());
        product.setStatus(new ProductStatus(entity.getStatus()));
        product.setVariants(entity.getVariants().stream().map(variantMapper::toDomain).collect(java.util.stream.Collectors.toList()));
        product.setCategoryIds(entity.getCategories().stream().map(category -> category.getId()).collect(java.util.stream.Collectors.toList()));
        return product;
    }
    
    public ProductEntity toEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        return entity;
    }
}
