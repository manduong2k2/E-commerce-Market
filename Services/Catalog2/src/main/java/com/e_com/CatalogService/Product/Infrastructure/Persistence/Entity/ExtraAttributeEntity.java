package com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity;

import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "extra_attributes")
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtraAttributeEntity extends JpaEntity{
    
    @Column(nullable = false)
    private String key;
    
    @Column(nullable = true)
    private String value;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variant_id")
    private ProductVariantEntity variant;
}