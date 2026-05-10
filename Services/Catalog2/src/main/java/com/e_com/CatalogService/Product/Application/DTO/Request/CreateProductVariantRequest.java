package com.e_com.CatalogService.Product.Application.DTO.Request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.e_com.CatalogService.Product.Domain.Model.ProductVariant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductVariantRequest {
    @NotBlank(message = "Variant code is required")
    private String code;
    
    @NotBlank(message = "Variant name is required")
    private String name;

    @NotNull(message = "Price is required")
    private double price;

    @Valid
    private List<CreateExtraAttributeRequest> extraAttributes;

    private List<MultipartFile> files;
    
    public ProductVariant toDomain() {
        ProductVariant variant = new ProductVariant();
        variant.setCode(code);
        variant.setName(name);
        variant.setPrice(price);
        variant.setExtraAttributes(extraAttributes != null ? extraAttributes.stream().map(CreateExtraAttributeRequest::toDomain).toList() : List.of());
        variant.setFiles(files);
        return variant;
    }
}
