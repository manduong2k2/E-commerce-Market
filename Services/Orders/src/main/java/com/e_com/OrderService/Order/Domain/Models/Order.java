package com.e_com.OrderService.Order.Domain.Models;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Constants.OrderStatusEnum;
import com.e_com.OrderService.Shared.Domain.AggregateRoot;

public class Order extends AggregateRoot<UUID> {
    private UUID userId;
    private OrderStatus status;
    private List<OrderItem> items;

    public Order(){
        super(null);
    }

    public Order(UUID id, UUID userId, OrderStatusEnum status, List<OrderItem> items) {
        super(id);
        this.userId = userId;
        this.status = new OrderStatus(status);
        this.items = items;
    }

    //Business methods;
    public void changeStatus(String status) {
        this.status.setValue(status);
    }

    public double getTotal() {
        return this.items.stream().mapToDouble(item -> item.getTotal()).sum();
    }

    //Base methods

    public UUID getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }
}
