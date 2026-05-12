package com.e_com.CatalogService.Product.Application.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.CatalogService.Product.Application.DTO.Request.CreateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Request.UpdateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Response.ProductResponse;
import com.e_com.CatalogService.Product.Domain.Constants.ProductStatusEnum;
import com.e_com.CatalogService.Product.Domain.Contract.IProductMapper;
import com.e_com.CatalogService.Product.Domain.Contract.IProductRepository;
import com.e_com.CatalogService.Product.Domain.Contract.IProductService;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Shared.Domain.Storage.IStorageService;
import com.e_com.CatalogService.Shared.Infrastructure.Event.EventOptions;
import com.e_com.CatalogService.Shared.Infrastructure.Event.IEventPublisher;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService {
    @Autowired
    public IProductRepository ProductRepository;
    @Autowired
    public IEventPublisher eventPublisher;

    @Autowired
    public IStorageService storageService;

    @Autowired
    public IProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return ProductRepository.findAll().stream().map(productMapper::toDomain)
                .map(ProductResponse::new)
                .toList();
    }

    public ProductResponse getProduct(UUID ProductId) {
        return ProductRepository.findById(ProductId)
                .map(productMapper::toDomain)
                .map(ProductResponse::new)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) throws IOException {
        Product product = request.toDomain();
        product.getVariants().forEach(variant -> variant.setId(UUID.randomUUID()));

        product.getVariants().forEach(variant -> {
            var files = variant.getFiles();
            if (files != null && !files.isEmpty()) {
                try {
                    storageService.uploadFiles("variant", files, "variant", variant.getId().toString());
                } catch (RuntimeException e) {
                    if (e.getCause() instanceof IOException) {
                        throw new RuntimeException("Failed to upload files for variant: " + variant.getId(),
                                e.getCause());
                    }
                    throw e;
                }
            }
        });

        product.setStatus(ProductStatusEnum.PUBLISHED);

        ProductEntity productEntity = ProductRepository.save(productMapper.toEntity(product));
        Product productDomain = productMapper.toDomain(productEntity);

        publishDomainEvents(productDomain, "Product.created");

        return new ProductResponse(productDomain);
    }

    @Transactional
    public ProductResponse updateProduct(UUID ProductId, UpdateProductRequest request) {
        ProductEntity productEntity = ProductRepository.findById(ProductId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product productDomain = productMapper.toDomain(productEntity);
        productDomain.setName(request.getName());
        productDomain.setDescription(request.getDescription());

        ProductEntity savedEntity = ProductRepository.save(productMapper.toEntity(productDomain));
        Product productUpdated = productMapper.toDomain(savedEntity);

        publishDomainEvents(productUpdated, "Product.updated");

        return new ProductResponse(productUpdated);
    }

    @Transactional
    public void deleteProduct(UUID ProductId) {
        ProductRepository.delete(ProductId);
    }

    @Async
    private void publishDomainEvents(Product Product, String queue) {
        Product.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        Product.clearDomainEvents();
    }
}
