package com.e_com.CatalogService.Product.Application.DTO.Response;

import java.util.List;
import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "code", "brandId", "description", "variants"})
public class ProductResponse {
    private UUID                    id;
    private String                  name;
    private String                  code;
    private UUID                    brandId;
    private String                  description;
    private List<ProductVariant>    variants;
    
    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.code = product.getCode();
        this.brandId = product.getBrandId();
        this.description = product.getDescription();
        this.variants = product.getVariants();
    }
}
