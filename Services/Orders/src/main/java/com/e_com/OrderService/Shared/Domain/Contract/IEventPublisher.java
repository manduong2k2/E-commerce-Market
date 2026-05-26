package com.e_com.OrderService.Shared.Domain.Contract;

import com.e_com.OrderService.Shared.Domain.DomainEvent;
import com.e_com.OrderService.Shared.Infrastructure.Event.EventOptions;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
