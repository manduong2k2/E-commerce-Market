package com.e_com.CatalogService.Shared.Infrastructure.Event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Shared.Domain.DomainEvent;
import com.e_com.CatalogService.Shared.Domain.Contract.IEventPublisher;

@Component
public class RabbitMQEventPublisher implements IEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(DomainEvent data, String queue) {
        rabbitTemplate.convertAndSend(queue, data);
    }

    @Override
    public void publish(DomainEvent event, EventOptions options) {
        sendMessage(event, options.getDestination());
    }
}
