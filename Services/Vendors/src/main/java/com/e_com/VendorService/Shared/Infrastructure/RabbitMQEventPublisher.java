package com.e_com.VendorService.Shared.Infrastructure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Optional;

public class RabbitMQEventPublisher implements EventPublisher {
    
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Object message, String queue) {
        rabbitTemplate.convertAndSend(queue, message);
    }

    @Override
    public void publish(Object event, Optional<HashMap<String, String>> parameters) {
        sendMessage(event, "vendor.created");
    }
}
