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
import com.e_com.CatalogService.Product.Domain.Contract.IProductRepository;
import com.e_com.CatalogService.Product.Domain.Contract.IProductService;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Shared.Domain.Contract.IEventPublisher;
import com.e_com.CatalogService.Shared.Domain.Contract.IMapper;
import com.e_com.CatalogService.Shared.Domain.Storage.IStorageService;
import com.e_com.CatalogService.Shared.Infrastructure.Event.EventOptions;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService {
    @Autowired
    public IProductRepository productRepository;

    @Autowired
    public IEventPublisher eventPublisher;

    @Autowired
    public IStorageService storageService;

    @Autowired
    public IMapper<Product, ProductEntity> productMapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .toList();
    }

    public ProductResponse getProduct(UUID ProductId) {
        return productRepository.findById(ProductId)
                .map(ProductResponse::new)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) throws IOException {
        Product product = request.toDomain();

        product.setStatus(ProductStatusEnum.PUBLISHED);

        Product savedProduct = productRepository.save(product);

        publishDomainEvents(savedProduct, "Product.created");

        return new ProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(UUID ProductId, UpdateProductRequest request) {
        Product product = productRepository.findById(ProductId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());

        Product savedProduct = productRepository.save(product);

        publishDomainEvents(savedProduct, "Product.updated");

        return new ProductResponse(savedProduct);
    }

    @Transactional
    public void deleteProduct(UUID ProductId) {
        productRepository.delete(ProductId);
    }

    @Async
    private void publishDomainEvents(Product Product, String queue) {
        Product.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        Product.clearDomainEvents();
    }
}
