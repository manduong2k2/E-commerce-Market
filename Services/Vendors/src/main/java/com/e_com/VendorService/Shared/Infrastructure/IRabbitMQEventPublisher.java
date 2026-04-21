package com.e_com.VendorService.Shared.Infrastructure;

public interface IRabbitMQEventPublisher {
    void publish(Object event, String queue);
}
