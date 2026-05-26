package com.e_com.OrderService.Order.Application.DTO.Responses;

import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Models.OrderItem;

import lombok.Data;

@Data
public class OrderItemResponse {
    private UUID id;
    private String productVariantId;
    private int quantity;
    private double total;
    private ProductSnapShotResponse snapShot;

    public static OrderItemResponse from(OrderItem item) {
        OrderItemResponse r = new OrderItemResponse();
        r.id               = item.getId();
        r.productVariantId = item.getProductVariantId();
        r.quantity         = item.getQuantity();
        r.total            = item.getTotal();
        r.snapShot         = ProductSnapShotResponse.from(item.getSnapShot());
        return r;
    }
}
