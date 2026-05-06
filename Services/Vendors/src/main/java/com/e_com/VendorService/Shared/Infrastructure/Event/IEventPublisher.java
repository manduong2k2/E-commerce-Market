package com.e_com.VendorService.Shared.Infrastructure.Event;

import com.e_com.VendorService.Shared.Domain.DomainEvent;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
