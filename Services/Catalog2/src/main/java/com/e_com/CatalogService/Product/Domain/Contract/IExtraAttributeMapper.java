package com.e_com.CatalogService.Product.Domain.Contract;

import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ExtraAttributeEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

public interface IExtraAttributeMapper extends IMapper<ExtraAttribute, ExtraAttributeEntity> {
    
}
