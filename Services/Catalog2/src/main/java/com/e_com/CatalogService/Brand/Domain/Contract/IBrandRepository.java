package com.e_com.CatalogService.Brand.Domain.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Brand.Domain.Model.Brand;

public interface IBrandRepository {
    Brand save(Brand brand);

    List<Brand> findAll();

    Optional<Brand> findById(UUID id);

    Optional<Brand> findByName(String name);

    Brand update(Brand brand);

    void delete(UUID id);
}
