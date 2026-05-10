package com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Nationalized;

import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends JpaEntity {
    @Column(nullable = false)
    @Size(max = 100)
    @Nationalized
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private UUID brandId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    @Size(max = 500)
    @Lob
    @Nationalized
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantEntity> variants;
    
    public ProductEntity() {}
    
    public ProductEntity(UUID id, String name, String description) {
        this.setId(id);
        this.name = name;
        this.description = description;
    }
}
