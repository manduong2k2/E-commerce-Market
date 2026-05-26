package com.e_com.OrderService.Order.Application.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.e_com.OrderService.Order.Application.DTO.Commands.CreateOrderCommand;
import com.e_com.OrderService.Order.Domain.Constants.OrderStatusEnum;
import com.e_com.OrderService.Order.Domain.Contracts.IOrderRepository;
import com.e_com.OrderService.Order.Domain.Events.OrderCreatedEvent;
import com.e_com.OrderService.Order.Domain.Model.Order;
import com.e_com.OrderService.Shared.Domain.Contract.IEventPublisher;
import com.e_com.OrderService.Shared.Infrastructure.Event.EventOptions;

public class OrderService {
    @Autowired
    private IOrderRepository repository;

    @Autowired
    public IEventPublisher eventPublisher;

    public void create(CreateOrderCommand command) {
        Order order = new Order(UUID.randomUUID(), command.getUserId(), OrderStatusEnum.CANCELLED, command.getItems());
        repository.create(order);

        order.addDomainEvent(new OrderCreatedEvent(
            order.getId(), order.getUserId(), order.getTotal()));

        publishDomainEvents(order, "order.created");
    }

    @Async
    private void publishDomainEvents(Order order, String queue) {
        order.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        order.clearDomainEvents();
    }
}
