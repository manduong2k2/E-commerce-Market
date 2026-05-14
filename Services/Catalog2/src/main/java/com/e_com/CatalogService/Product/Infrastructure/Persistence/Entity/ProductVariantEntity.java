package com.e_com.CatalogService.Product.Infrastructure.Persistence.Entity;

import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.e_com.CatalogService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_variants")
@EqualsAndHashCode(callSuper = false)
public class ProductVariantEntity extends JpaEntity{
    @Column(nullable = false)
    @Size(max = 100)
    @Nationalized
    private String name;

    @Column(nullable = false)
    private String code;
    
    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ExtraAttributeEntity> extraAttributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<ExtraAttributeEntity> getExtraAttributes() { return extraAttributes; }
    public void setExtraAttributes(List<ExtraAttributeEntity> extraAttributes) { this.extraAttributes = extraAttributes; }

    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }
}

