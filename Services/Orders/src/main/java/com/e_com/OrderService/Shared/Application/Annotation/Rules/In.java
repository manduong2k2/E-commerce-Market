package com.e_com.OrderService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.e_com.OrderService.Shared.Application.Annotation.Validators.InValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InValidator.class)
public @interface In {
    String message() default "Value is not in allowed list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values();
}