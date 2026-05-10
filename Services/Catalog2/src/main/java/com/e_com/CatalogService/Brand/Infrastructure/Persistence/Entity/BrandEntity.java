package com.e_com.CatalogService.Brand.Infrastructure.Persistence.Entity;

import java.util.UUID;

import org.hibernate.annotations.Nationalized;

import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "brands")
@Data
@EqualsAndHashCode(callSuper = false)
public class BrandEntity extends JpaEntity {
    @Column(nullable = false)
    @Size(max = 100)
    @Nationalized
    private String name;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    @Size(max = 500)
    @Lob
    @Nationalized
    private String description;

    public BrandEntity() {}

    public BrandEntity(UUID id, String name, String image, String description) {
        this.setId(id);
        this.name = name;
        this.image = image;
        this.description = description;
    }
}