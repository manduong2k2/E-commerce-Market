package com.e_com.OrderService.Order.Infrastructure.Persistence.Entities;

import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Shared.Infrastructure.Persistence.JpaEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_snapshots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductSnapShotJpaEntity extends JpaEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItemJpaEntity orderItem;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "brand")
    private String brand;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "product_snapshot_categories",
        joinColumns = @JoinColumn(name = "snapshot_id")
    )
    @Column(name = "category")
    private List<String> categories;

    @Column(name = "variant_id", nullable = false)
    private UUID variantId;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "variant_code")
    private String code;

    @Column(name = "variant_name")
    private String name;
}
