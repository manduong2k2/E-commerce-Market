package com.e_com.CatalogService.Product.Domain.Model;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.e_com.CatalogService.Shared.Domain.AggregateRoot;

public class ProductVariant extends AggregateRoot<UUID> {
    private UUID id;
    private UUID productId;
    private String name;
    private String code;
    private Money price = new Money(0);
    private List<ExtraAttribute> extraAttributes;
    private List<MultipartFile> files;
    
    public ProductVariant() {
        super(UUID.randomUUID());
    }
    
    public ProductVariant(UUID id, UUID productId, String name, String code, double price) {
        super(id);
        this.productId = productId;
        this.name = name;
        this.code = code;
        this.price = new Money(price);
        this.extraAttributes = new java.util.ArrayList<>();
    }

    public ProductVariant(String name, String code, double price, List<ExtraAttribute> extraAttributes) {
        super(UUID.randomUUID());
        this.productId = null;
        this.name = name;
        this.code = code;
        this.price = new Money(price);
        this.extraAttributes = extraAttributes;
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getProductId() {
        return productId;
    }
    
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Money getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = new Money(price);
    }
    
    public List<ExtraAttribute> getExtraAttributes() {
        return extraAttributes;
    }
    
    public void setExtraAttributes(List<ExtraAttribute> extraAttributes) {
        this.extraAttributes = extraAttributes;
    }
    
    public List<MultipartFile> getFiles() {
        return files;
    }
    
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
