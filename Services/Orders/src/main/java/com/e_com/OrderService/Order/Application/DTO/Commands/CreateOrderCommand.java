package com.e_com.OrderService.Order.Application.DTO.Commands;

import java.util.List;
import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Model.OrderItem;

import lombok.Data;

@Data
public class CreateOrderCommand{
    private UUID cartId;
    private UUID userId;
    private List<OrderItem> items;
}
