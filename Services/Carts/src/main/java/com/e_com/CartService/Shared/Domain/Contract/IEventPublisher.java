package com.e_com.CartService.Shared.Domain.Contract;

import com.e_com.CartService.Shared.Domain.DomainEvent;
import com.e_com.CartService.Shared.Infrastructure.Event.EventOptions;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
