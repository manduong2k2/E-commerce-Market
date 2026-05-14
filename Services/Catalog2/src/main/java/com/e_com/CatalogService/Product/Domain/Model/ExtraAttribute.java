package com.e_com.CatalogService.Product.Domain.Model;

import java.util.UUID;

import com.e_com.CatalogService.Shared.Domain.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "key", "value", "productVariantId"})
public class ExtraAttribute extends AggregateRoot<UUID> {
    private UUID productVariantId;
    private String key;
    private String value;

    public ExtraAttribute() {
        super(null);
    }

    public ExtraAttribute(UUID productVariantId, String key, String value) {
        super(null);
        this.productVariantId = productVariantId;
        this.key = key;
        this.value = value;
    }

    public ExtraAttribute(String key, String value) {
        super(null);
        this.key = key;
        this.value = value;
    }

    public UUID getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(UUID productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
