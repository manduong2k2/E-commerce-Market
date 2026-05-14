package com.e_com.CatalogService.Product.Application.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.e_com.CatalogService.Shared.Application.Annotation.Auth.Authenticated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAll() {        
        List<ProductResponse> products = productService.getAllProducts();

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data",  products);
        
        return ResponseEntity.ok().body(response);
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> create(@Valid @ModelAttribute CreateProductRequest request) throws IOException {
        ProductResponse created = productService.createProduct(request.toDomain());

        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", created);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Authenticated
    @GetMapping("/{productId}")
    public ResponseEntity<HashMap<String, Object>> details(@PathVariable UUID productId) {
        ProductResponse product = productService.getProduct(productId);
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", product);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @Authenticated
    @PutMapping("/{productId}")
    public ResponseEntity<HashMap<String, Object>> update(@PathVariable UUID productId, @Valid @ModelAttribute UpdateProductRequest request) {
        ProductResponse updated = productService.updateProduct(productId, request.toDomain());
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", updated);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @Authenticated
    @DeleteMapping("/{productId}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Product deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}