package com.e_com.OrderService.Shared.Application.Errors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private String className;
    private List<String> stackTrace;
}
