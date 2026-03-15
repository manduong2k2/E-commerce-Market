package com.e_com.AuthService.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Boolean success;
    private String message;
}
