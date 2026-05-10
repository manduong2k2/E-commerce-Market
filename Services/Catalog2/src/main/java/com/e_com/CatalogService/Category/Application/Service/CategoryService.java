package com.e_com.CatalogService.Category.Application.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.CatalogService.Category.Application.DTO.Request.CreateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Request.UpdateCategoryRequest;
import com.e_com.CatalogService.Category.Application.DTO.Response.CategoryResponse;
import com.e_com.CatalogService.Category.Domain.Contract.ICategoryRepository;
import com.e_com.CatalogService.Category.Domain.Model.Category;
import com.e_com.CatalogService.Shared.Domain.Contract.ICategoryService;
import com.e_com.CatalogService.Shared.Infrastructure.Event.EventOptions;
import com.e_com.CatalogService.Shared.Infrastructure.Event.IEventPublisher;

import jakarta.transaction.Transactional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    public ICategoryRepository CategoryRepository;
    @Autowired
    public IEventPublisher eventPublisher; 

    public List<CategoryResponse> getAllCategorys() {
        return CategoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .toList();
    }

    public CategoryResponse getCategory(UUID CategoryId) {
        return CategoryRepository.findById(CategoryId)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category Category = new Category(
                null,
                request.getName(),
                request.getParentId(),
                request.getImage(),
                request.getDescription()
        );

        Category = CategoryRepository.save(Category);

        publishDomainEvents(Category, "Category.created");

        return new CategoryResponse(Category);
    }

    @Transactional
    public CategoryResponse updateCategory(UUID CategoryId, UpdateCategoryRequest request) {
        Category Category = CategoryRepository.findById(CategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category.setName(request.getName());
        Category.setDescription(request.getDescription());

        Category = CategoryRepository.save(Category);

        publishDomainEvents(Category, "Category.updated");
        
        return new CategoryResponse(Category);
    }

    @Transactional
    public void deleteCategory(UUID CategoryId) {
        CategoryRepository.delete(CategoryId);
    }

    @Async
    private void publishDomainEvents(Category Category, String queue) {
        Category.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        Category.clearDomainEvents();
    }
}
