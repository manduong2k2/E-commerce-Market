package com.e_com.CartService.Shared.Infrastructure.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOptions {
    private String destination; 
    private boolean forceExternal;
}
