package com.e_com.CatalogService.Product.Infrastructure.Mapper;

import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ExtraAttributeEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

@Component
public class ExtraAttributeMapper implements IMapper<ExtraAttribute, ExtraAttributeEntity>{
    public ExtraAttribute toDomain(ExtraAttributeEntity entity) {
        ExtraAttribute extraAttribute = new ExtraAttribute();
        extraAttribute.setId(entity.getId());
        extraAttribute.setKey(entity.getKey());
        extraAttribute.setValue(entity.getValue());
        extraAttribute.setProductVariantId(entity.getVariant().getId());
        return extraAttribute;
    }

    public ExtraAttributeEntity toEntity(ExtraAttribute domain) {
        ExtraAttributeEntity entity = new ExtraAttributeEntity();
        entity.setId(domain.getId());
        entity.setKey(domain.getKey());
        entity.setValue(domain.getValue());
        return entity;
    }
}