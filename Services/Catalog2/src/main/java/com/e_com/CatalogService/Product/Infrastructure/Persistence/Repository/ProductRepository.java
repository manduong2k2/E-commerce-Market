package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Contract.IProductRepository;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;

@Repository
public class ProductRepository implements IProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepository(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ProductEntity> findAll() {
        return jpaRepository.findAll().stream()
                .toList();
    }

    @Override
    public ProductEntity save(ProductEntity Product) {
        ProductEntity saved = jpaRepository.save(Product);
        return saved;
    }

    @Override
    public Optional<ProductEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findByName(String name) {
        return jpaRepository.findByName(name);
    }
    
    @Override
    public ProductEntity update(ProductEntity Product) {
        return jpaRepository.save(Product);
    }
    
    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}