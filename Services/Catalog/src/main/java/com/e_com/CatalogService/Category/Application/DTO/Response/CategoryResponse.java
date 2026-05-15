package com.e_com.CatalogService.Category.Application.DTO.Response;

import java.util.UUID;

import com.e_com.CatalogService.Category.Domain.Model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private UUID id;
    private String name;

    public CategoryResponse(Category Category) {
        this.id = Category.getId();
        this.name = Category.getName();
    }
}
