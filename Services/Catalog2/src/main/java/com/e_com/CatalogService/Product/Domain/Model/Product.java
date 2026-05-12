package com.e_com.CatalogService.Product.Domain.Model;

import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Constants.ProductStatusEnum;
import com.e_com.CatalogService.Shared.Domain.AggregateRoot;

import java.util.ArrayList;
import java.util.List;

public class Product extends AggregateRoot<UUID> {
    private String name;
    private String description;
    private String code;
    private UUID brandId;
    private List<UUID> categoryIds;
    private ProductStatus status;
    private List<ProductVariant> variants;

    public Product() {
        super(UUID.randomUUID());
        this.status = new ProductStatus();
        this.variants = new ArrayList<>();
        this.categoryIds = new ArrayList<>();
    }

    public Product(UUID id, String name, String description, String code, UUID brandId, ProductStatus status, List<ProductVariant> variants, List<UUID> categoryIds) {
        super(id);
        this.name = name;
        this.description = description;
        this.code = code;
        this.brandId = brandId;
        this.status = status;
        this.variants = variants;
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public UUID getBrandId() {
        return brandId;
    }
    
    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<ProductVariant> getVariants() {
        return variants;
    }
    
    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
    
    public List<UUID> getCategoryIds() {
        return categoryIds;
    }
    
    public void setCategoryIds(List<UUID> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getStatus() {
        return status.getValue();
    }
    
    public void setStatus(ProductStatusEnum status) {
        this.status.setValue(status);
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
