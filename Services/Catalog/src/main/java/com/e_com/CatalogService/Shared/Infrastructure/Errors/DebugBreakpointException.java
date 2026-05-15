package com.e_com.CatalogService.Shared.Infrastructure.Errors;

import java.util.HashMap;
import java.util.Map;

public class DebugBreakpointException extends RuntimeException {
    
    private final Map<String, Object> debugInfo;

    public DebugBreakpointException(String message, Object obj) {
        super("Debug breakpoint");
        this.debugInfo = new HashMap<>();
        this.debugInfo.put(message, obj);
    }

    public Map<String, Object> getDebugInfo() {
        return debugInfo;
    }
}
