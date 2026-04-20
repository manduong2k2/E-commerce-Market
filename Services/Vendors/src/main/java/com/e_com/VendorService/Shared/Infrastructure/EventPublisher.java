package com.e_com.VendorService.Shared.Infrastructure;

import java.util.HashMap;
import java.util.Optional;

public interface EventPublisher {
    void publish(Object event, Optional<HashMap<String, String>> parameters);
}
