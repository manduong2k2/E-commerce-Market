package com.e_com.CatalogService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.e_com.CatalogService.Shared.Application.Annotation.Validators.ExistListValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistListValidator.class)
public @interface ExistList {
    String message() default "One or more values do not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Exist[] value();
}
