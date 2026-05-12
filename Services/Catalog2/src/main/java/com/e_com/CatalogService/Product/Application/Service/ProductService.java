package com.e_com.CatalogService.Product.Application.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public ProductResponse createProduct(Product product) throws IOException {
        product.setStatus(ProductStatusEnum.PUBLISHED);

        Product savedProduct = productRepository.save(product);

        publishDomainEvents(savedProduct, "Product.created");

        return new ProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setCode(updatedProduct.getCode());
        product.setBrandId(updatedProduct.getBrandId());
        product.setCategoryIds(updatedProduct.getCategoryIds());

        Product savedProduct = productRepository.save(product);

        publishDomainEvents(savedProduct, "Product.updated");

        return new ProductResponse(savedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.delete(productId);
    }

    @Async
    private void publishDomainEvents(Product product, String queue) {
        product.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        product.clearDomainEvents();
    }
}
