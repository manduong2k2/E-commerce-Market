package com.e_com.OrderService.Order.Application.DTO.Responses;

import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Models.ProductSnapShot;

import lombok.Data;

@Data
public class ProductSnapShotResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private String productCode;
    private String brand;
    private List<String> categories;
    private UUID variantId;
    private String variantName;
    private String variantCode;
    private double price;

    public static ProductSnapShotResponse from(ProductSnapShot snap) {
        if (snap == null) return null;
        ProductSnapShotResponse r = new ProductSnapShotResponse();
        r.id           = snap.getId();
        r.productId    = snap.getProductId();
        r.productName  = snap.getProductName();
        r.productCode  = snap.getProductCode();
        r.brand        = snap.getBrand();
        r.categories   = snap.getCategories();
        r.variantId    = snap.getVariantId();
        r.variantName  = snap.getName();
        r.variantCode  = snap.getCode();
        r.price        = snap.getPrice();
        return r;
    }
}
