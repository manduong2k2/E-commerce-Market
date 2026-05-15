package com.e_com.CatalogService.Product.Domain.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Model.Product;


public interface IProductRepository {
    Product save(Product Product);

    List<Product> findAll();

    Optional<Product> findById(UUID id);

    List<Product> findByName(String name);

    Product update(Product Product);

    void delete(UUID id);
}
