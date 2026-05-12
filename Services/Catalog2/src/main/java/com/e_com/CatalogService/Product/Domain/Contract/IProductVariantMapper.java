package com.e_com.CatalogService.Product.Domain.Contract;

import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

public interface IProductVariantMapper extends IMapper<ProductVariant, ProductVariantEntity> {
    
}
