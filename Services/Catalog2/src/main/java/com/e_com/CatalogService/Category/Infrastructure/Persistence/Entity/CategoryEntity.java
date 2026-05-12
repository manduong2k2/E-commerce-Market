package com.e_com.CatalogService.Category.Infrastructure.Persistence.Entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity.ProductEntity;
import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryEntity extends JpaEntity {
    @Column(nullable = false)
    @Size(max = 100)
    @Nationalized
    private String name;
    
    @Column(nullable = true)
    private UUID parentId;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    @Size(max = 500)
    @Lob
    @Nationalized
    private String description;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductEntity> products;

    public CategoryEntity() {}

    public CategoryEntity(UUID id, String name, UUID parentId, String image, String description) {
        this.setId(id);
        this.name = name;
        this.parentId = parentId;
        this.image = image;
        this.description = description;
    }
}