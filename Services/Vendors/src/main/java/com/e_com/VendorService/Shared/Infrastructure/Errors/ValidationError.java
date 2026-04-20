package com.e_com.VendorService.Shared.Infrastructure.Errors;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private String message;
    private Map<String, String> errors;
}
