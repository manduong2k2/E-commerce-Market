package com.e_com.OrderService.Order.Infrastructure.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.e_com.OrderService.Order.Domain.Constants.OrderStatusEnum;
import com.e_com.OrderService.Order.Domain.Models.Order;
import com.e_com.OrderService.Order.Domain.Models.OrderItem;
import com.e_com.OrderService.Order.Domain.Models.ProductSnapShot;
import com.e_com.OrderService.Order.Infrastructure.Persistence.Entities.OrderItemJpaEntity;
import com.e_com.OrderService.Order.Infrastructure.Persistence.Entities.OrderJpaEntity;
import com.e_com.OrderService.Order.Infrastructure.Persistence.Entities.ProductSnapShotJpaEntity;

@Component
public class OrderMapper {

    // ─── Domain → JPA ────────────────────────────────────────────────────────────

    public OrderJpaEntity toJpa(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setUserId(order.getUserId());
        entity.setStatus(order.getStatus().getValue());

        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
                .map(item -> toItemJpa(item, entity))
                .toList();

        entity.setItems(itemEntities);
        return entity;
    }

    private OrderItemJpaEntity toItemJpa(OrderItem item, OrderJpaEntity orderEntity) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setId(item.getId());
        entity.setOrder(orderEntity);
        entity.setProductVariantId(item.getProductVariantId());
        entity.setQuantity(item.getQuantity());

        if (item.getSnapShot() != null) {
            entity.setSnapShot(toSnapShotJpa(item.getSnapShot(), entity));
        }

        return entity;
    }

    private ProductSnapShotJpaEntity toSnapShotJpa(ProductSnapShot snapShot, OrderItemJpaEntity itemEntity) {
        ProductSnapShotJpaEntity entity = new ProductSnapShotJpaEntity();
        entity.setId(snapShot.getId());
        entity.setOrderItem(itemEntity);
        entity.setProductId(snapShot.getProductId());
        entity.setProductName(snapShot.getProductName());
        entity.setProductCode(snapShot.getProductCode());
        entity.setBrand(snapShot.getBrand());
        entity.setCategories(snapShot.getCategories());
        entity.setVariantId(snapShot.getVariantId());
        entity.setPrice(snapShot.getPrice());
        entity.setCode(snapShot.getCode());
        entity.setName(snapShot.getName());
        return entity;
    }

    // ─── JPA → Domain ────────────────────────────────────────────────────────────

    public Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(this::toItemDomain)
                .toList();

        return new Order(
                entity.getId(),
                entity.getUserId(),
                OrderStatusEnum.valueOf(entity.getStatus()),
                items
        );
    }

    private OrderItem toItemDomain(OrderItemJpaEntity entity) {
        OrderItem item = new OrderItem(
                entity.getId(),
                entity.getProductVariantId(),
                entity.getQuantity()
        );

        if (entity.getSnapShot() != null) {
            item.setSnapShot(toSnapShotDomain(entity.getSnapShot()));
        }

        return item;
    }

    private ProductSnapShot toSnapShotDomain(ProductSnapShotJpaEntity entity) {
        return new ProductSnapShot(
                entity.getId(),
                entity.getProductId(),
                entity.getProductName(),
                entity.getProductCode(),
                entity.getBrand(),
                entity.getCategories(),
                entity.getVariantId(),
                entity.getPrice(),
                entity.getCode(),
                entity.getName()
        );
    }

    // ─── Update helper (dùng khi update) ─────────────────────────────────────────

    public void updateJpaFromDomain(Order order, OrderJpaEntity entity) {
        entity.setStatus(order.getStatus().getValue());

        // Xoá items cũ, thêm lại từ domain
        entity.getItems().clear();
        List<OrderItemJpaEntity> updatedItems = order.getItems().stream()
                .map(item -> toItemJpa(item, entity))
                .toList();
        entity.getItems().addAll(updatedItems);
    }
}
