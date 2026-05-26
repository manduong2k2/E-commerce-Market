package com.e_com.OrderService.Order.Application.DTO.Responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Models.Order;

import lombok.Data;

@Data
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private String status;
    private double total;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        OrderResponse r = new OrderResponse();
        r.id        = order.getId();
        r.userId    = order.getUserId();
        r.status    = order.getStatus().getValue();
        r.total     = order.getTotal();
        r.items     = order.getItems().stream().map(OrderItemResponse::from).toList();
        r.createdAt = order.getCreatedAt();
        r.updatedAt = order.getUpdatedAt();
        return r;
    }
}
