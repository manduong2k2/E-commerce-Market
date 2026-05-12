package com.e_com.CatalogService.Product.Application.Mapper;

import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ExtraAttributeEntity;

public class ExtraAttributeMapper{
    public static ExtraAttribute toDomain(ExtraAttributeEntity entity) {
        ExtraAttribute extraAttribute = new ExtraAttribute();
        extraAttribute.setId(entity.getId());
        extraAttribute.setKey(entity.getKey());
        extraAttribute.setValue(entity.getValue());
        return extraAttribute;
    }
    
    public static ExtraAttributeEntity toEntity(ExtraAttribute domain) {
        ExtraAttributeEntity entity = new ExtraAttributeEntity();
        entity.setId(domain.getId());
        entity.setKey(domain.getKey());
        entity.setValue(domain.getValue());
        return entity;
    }
}
