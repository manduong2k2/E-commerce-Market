package com.e_com.CatalogService.Shared.Infrastructure.Event;

import com.e_com.CatalogService.Shared.Domain.DomainEvent;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
