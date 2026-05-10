package com.e_com.CatalogService.Product.Infrastructure.Persistence.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CatalogService.Product.Domain.Contract.IProductRepository;
import com.e_com.CatalogService.Product.Domain.Model.ExtraAttribute;
import com.e_com.CatalogService.Product.Domain.Model.Money;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Domain.Model.ProductStatus;
import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ExtraAttributeEntity;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductVariantEntity;

@Repository
public class ProductRepository implements IProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepository(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Product save(Product Product) {
        ProductEntity entity = toEntity(Product);
        ProductEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Product> findByName(String name) {
        return jpaRepository.findByName(name)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    @Override
    public Product update(Product Product) {
        ProductEntity entity = toEntity(Product);
        ProductEntity updated = jpaRepository.save(entity);
        return toDomain(updated);
    }
    
    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    // ===== mapping =====

    private Product toDomain(ProductEntity entity) {
        Product product = new Product();
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setCode(entity.getCode());
        product.setBrandId(entity.getBrandId());
        product.setStatus(new ProductStatus(entity.getStatus()));

        product.setVariants(entity.getVariants().stream()
                .map(variant -> {
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setName(variant.getName());
                    productVariant.setCode(variant.getCode());
                    productVariant.setPrice(new Money(variant.getPrice()).getValue());
                    productVariant.setExtraAttributes(variant.getExtraAttributes().stream()
                            .map(extraAttribute -> {
                                ExtraAttribute extraAttributeDomain = new ExtraAttribute();
                                extraAttributeDomain.setKey(extraAttribute.getKey());
                                extraAttributeDomain.setValue(extraAttribute.getValue());
                                extraAttributeDomain.setProductVariantId(variant.getId());
                                return extraAttributeDomain;
                            })
                            .toList());
                    return productVariant;
                })
                .toList());

        return product;
    }

    private ProductEntity toEntity(Product Product) {
        ProductEntity entity = new ProductEntity();
        entity.setName(Product.getName());
        entity.setDescription(Product.getDescription());
        entity.setCode(Product.getCode());
        entity.setBrandId(Product.getBrandId());
        entity.setStatus(Product.getStatus());

        List<ProductVariantEntity> variants = Product.getVariants().stream()
                .map(variant -> {
                    ProductVariantEntity variantEntity = new ProductVariantEntity();
                    variantEntity.setName(variant.getName());
                    variantEntity.setCode(variant.getCode());
                    variantEntity.setPrice(variant.getPrice().getValue());
                    variantEntity.setProduct(entity);

                    List<ExtraAttributeEntity> extraAttributes = variant.getExtraAttributes().stream()
                            .map(extraAttribute -> {
                                ExtraAttributeEntity extraAttributeEntity = new ExtraAttributeEntity();
                                extraAttributeEntity.setKey(extraAttribute.getKey());
                                extraAttributeEntity.setValue(extraAttribute.getValue());
                                extraAttributeEntity.setVariant(variantEntity);
                                return extraAttributeEntity;
                            })
                            .toList();
                    variantEntity.setExtraAttributes(extraAttributes);

                    return variantEntity;
                })
                .toList();
        entity.setVariants(variants);

        return entity;
    }
}