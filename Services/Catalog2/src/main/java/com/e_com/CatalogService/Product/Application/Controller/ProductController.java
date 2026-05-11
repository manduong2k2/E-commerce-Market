package com.e_com.CatalogService.Product.Application.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.CatalogService.Product.Application.DTO.Request.CreateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Request.UpdateProductRequest;
import com.e_com.CatalogService.Product.Application.DTO.Response.ProductResponse;
import com.e_com.CatalogService.Product.Domain.Contract.IProductService;
import com.e_com.CatalogService.Shared.Infrastructure.Annotation.Auth.Authenticated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Authenticated
    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllProducts();
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> create(@Valid @ModelAttribute CreateProductRequest request) throws IOException {
        ProductResponse created = productService.createProduct(request);

        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Authenticated
    @GetMapping("/{productId}")
    public ProductResponse details(@PathVariable UUID productId) {
        return productService.getProduct(productId);
    }
    
    @Authenticated
    @PutMapping("/{productId}")
    public ProductResponse update(@PathVariable UUID productId, @Valid @ModelAttribute UpdateProductRequest request) {
        return productService.updateProduct(productId, request);
    }
    
    @Authenticated
    @DeleteMapping("/{productId}")
    public void delete(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
    }
}