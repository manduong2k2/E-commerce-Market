package com.e_com.VendorService.Shared.Domain.Contract;

import com.e_com.VendorService.Shared.Domain.DomainEvent;
import com.e_com.VendorService.Shared.Infrastructure.Event.EventOptions;

public interface IEventPublisher {
    void publish(DomainEvent event, EventOptions options);
}
