package com.e_com.CatalogService.Product.Domain.Contract;

import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;

public interface IProductMapper extends IMapper<Product, ProductEntity> {
}
