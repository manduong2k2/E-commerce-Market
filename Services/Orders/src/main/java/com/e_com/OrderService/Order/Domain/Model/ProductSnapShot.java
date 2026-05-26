package com.e_com.OrderService.Order.Domain.Model;

import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Shared.Domain.Entity;

public class ProductSnapShot extends Entity<UUID>{
    private UUID productId;
    private String productName;
    private String productCode;
    private String brand;
    private List<String> categories;
    
    private UUID variantId;
    private double price;
    private String code;
    private String name;

    public ProductSnapShot() {
        super(null);
    }

    public ProductSnapShot(UUID id, UUID productId, String productName, String productCode, String brand,
            List<String> categories, UUID variantId, double price, String code, String name) {
        super(id);
        this.productId = productId;
        this.productName = productName;
        this.productCode = productCode;
        this.brand = brand;
        this.categories = categories;
        this.variantId = variantId;
        this.price = price;
        this.code = code;
        this.name = name;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
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
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public List<String> getCategories() {
        return categories;
    }
    
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
