package com.e_com.OrderService.Shared.Infrastructure.Utils;

import com.e_com.OrderService.Shared.Infrastructure.Errors.DebugBreakpointException;

public class Debugger {
    public static void debug(String message, Object obj) {
        throw new DebugBreakpointException(message, obj);
    }
}
