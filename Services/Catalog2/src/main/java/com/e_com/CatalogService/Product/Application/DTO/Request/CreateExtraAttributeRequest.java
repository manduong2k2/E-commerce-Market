package com.e_com.CatalogService.Product.Application.DTO.Request;

import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;

public class CreateExtraAttributeRequest {
    private String key;
    private String value;
    
    public CreateExtraAttributeRequest() {}
    
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
    
    public ExtraAttribute toDomain() {
        return new ExtraAttribute(key, value);
    }
}
