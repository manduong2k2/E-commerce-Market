package com.e_com.CatalogService.Product.Application.DTO.Request;

import java.util.List;
import java.util.UUID;
import com.e_com.CatalogService.Product.Domain.Model.Product;
import com.e_com.CatalogService.Shared.Application.Annotation.Rules.Exist;
import com.e_com.CatalogService.Shared.Application.Annotation.Rules.ExistList;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Product code is required")
    private String code;

    @NotNull(message = "Brand ID is required")
    @Exist(table = "brands", column = "id", message = "Brand not found")
    private UUID brandId;
    
    @Nullable
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    
    @Nullable
    @ExistList(value = @Exist(table = "categories", column = "id", message = "Category not found"))
    private List<UUID> categoryIds;

    public Product toDomain() {
        Product product = new Product(
            null,
            name,
            description,
            code,
            brandId,
            null,
            null,
            categoryIds != null ? categoryIds : List.of()
        );
        return product;
    }
}

