package com.e_com.OrderService.Order.Application.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.OrderService.Order.Application.DTO.Commands.CreateOrderCommand;
import com.e_com.OrderService.Order.Application.DTO.Responses.OrderResponse;
import com.e_com.OrderService.Order.Domain.Constants.OrderStatusEnum;
import com.e_com.OrderService.Order.Domain.Contracts.IOrderRepository;
import com.e_com.OrderService.Order.Domain.Models.Order;
import com.e_com.OrderService.Shared.Domain.Contract.IEventPublisher;
import com.e_com.OrderService.Shared.Infrastructure.Event.EventOptions;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository repository;

    @Autowired
    public IEventPublisher eventPublisher;

    public List<OrderResponse> list() {
        return repository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse findById(UUID id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return OrderResponse.from(order);
    }

    public void create(CreateOrderCommand command) {
        Order order = new Order(null, command.getUserId(), OrderStatusEnum.PENDING, command.getItems());
        repository.create(order);
    }

    @Async
    private void publishDomainEvents(Order order, String queue) {
        order.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        order.clearDomainEvents();
    }
}
