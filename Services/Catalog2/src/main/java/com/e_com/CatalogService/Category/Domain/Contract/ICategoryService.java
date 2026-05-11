package com.e_com.CatalogService.Category.Domain.Contract;

import java.util.List;
import java.util.UUID;

import com.e_com.CatalogService.Category.Application.DTO.Request.CreateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Request.UpdateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Response.CategoryResponse;

public interface ICategoryService {
    public List<CategoryResponse> getAllCategorys();

    public CategoryResponse createCategory(CreateCategoryRequest request);

    public CategoryResponse getCategory(UUID id);

    public CategoryResponse updateCategory(UUID id, UpdateCategoryRequest request);

    public void deleteCategory(UUID id);

}
