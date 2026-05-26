package com.e_com.CartService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.e_com.CartService.Shared.Application.Annotation.Validators.DistinctValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DistinctValidator.class)
public @interface Distinct {
    String message() default "List contains duplicated values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}