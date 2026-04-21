package com.e_com.VendorService.Shared.Infrastructure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements IRabbitMQEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Object message, String queue) {
        rabbitTemplate.convertAndSend(queue, message);
    }

    @Override
    public void publish(Object event, String queue) {
        sendMessage(event, queue);
    }
}
