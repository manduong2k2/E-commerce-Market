package com.e_com.CatalogService.Category.Application.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.CatalogService.Category.Application.DTO.Request.CreateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Request.UpdateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Response.CategoryResponse;
import com.e_com.CatalogService.Shared.Domain.Contract.ICategoryService;
import com.e_com.CatalogService.Shared.Infrastructure.Annotation.Auth.Authenticated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private ICategoryService CategoryService;

    @Authenticated
    @GetMapping
    public List<CategoryResponse> getAll() {
        return CategoryService.getAllCategorys();
    }

    @Authenticated
    @PostMapping
    public CategoryResponse create(@Valid @ModelAttribute CreateCategoryRequest request) {
        return CategoryService.createCategory(request);
    }

    @Authenticated
    @GetMapping("/{categoryId}")
    public CategoryResponse details(@PathVariable UUID categoryId) {
        return CategoryService.getCategory(categoryId);
    }
    
    @Authenticated
    @PutMapping("/{categoryId}")
    public CategoryResponse update(@PathVariable UUID categoryId, @Valid @ModelAttribute UpdateCategoryRequest request) {
        return CategoryService.updateCategory(categoryId, request);
    }
    
    @Authenticated
    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable UUID categoryId) {
        CategoryService.deleteCategory(categoryId);
    }
}