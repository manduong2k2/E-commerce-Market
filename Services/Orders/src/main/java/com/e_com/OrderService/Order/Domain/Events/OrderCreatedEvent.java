package com.e_com.OrderService.Order.Domain.Events;

import java.util.UUID;

import com.e_com.OrderService.Shared.Domain.DomainEvent;

public class OrderCreatedEvent extends DomainEvent{
    private final UUID orderId;

    private final UUID userId;

    private final double totalPrice;

    public OrderCreatedEvent(
            UUID orderId,
            UUID userId,
            double totalPrice
    ) {

        super();

        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
