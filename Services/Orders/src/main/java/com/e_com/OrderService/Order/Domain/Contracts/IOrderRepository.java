package com.e_com.OrderService.Order.Domain.Contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.OrderService.Order.Domain.Models.Order;

public interface IOrderRepository {
    List<Order> findAll();
    Order create(Order order);
    Optional<Order> findById(UUID id);
    Order update(Order order);
    void delete(UUID id);
}
