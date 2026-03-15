package com.e_com.AuthService.Validation;

import com.e_com.AuthService.Annotation.Rules.Exist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {
    @NotBlank(message = "email must not be empty")
    @Exist(table = "users", column = "email", message = "Email does not exist")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
}
