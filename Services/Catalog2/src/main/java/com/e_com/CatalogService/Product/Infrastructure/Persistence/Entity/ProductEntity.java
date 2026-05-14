package com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.e_com.CatalogService.Brand.Infrastructure.Persistence.Entity.BrandEntity;
import com.e_com.CatalogService.Category.Infrastructure.Persistence.Entity.CategoryEntity;
import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products")
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends JpaEntity {
    @Column(nullable = false)
    @Size(max = 100)
    @Nationalized
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CategoryEntity> categories;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    @Size(max = 500)
    @Nationalized
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductVariantEntity> variants;
    
    public ProductEntity() {}
    
    public ProductEntity(UUID id, String name, String description) {
        this.setId(id);
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public BrandEntity getBrand() { return brand; }
    public void setBrand(BrandEntity brand) { this.brand = brand; }

    public List<CategoryEntity> getCategories() { return categories; }
    public void setCategories(List<CategoryEntity> categories) { this.categories = categories; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ProductVariantEntity> getVariants() { return variants; }
    public void setVariants(List<ProductVariantEntity> variants) { this.variants = variants; }
}
