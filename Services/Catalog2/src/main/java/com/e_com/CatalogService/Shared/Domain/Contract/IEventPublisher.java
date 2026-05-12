package com.e_com.CatalogService.Shared.Domain.Contract;

import com.e_com.CatalogService.Shared.Domain.DomainEvent;
import com.e_com.CatalogService.Shared.Infrastructure.Event.EventOptions;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
