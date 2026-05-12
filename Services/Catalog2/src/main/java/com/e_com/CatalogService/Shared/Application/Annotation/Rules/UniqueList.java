package com.e_com.CatalogService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.e_com.CatalogService.Shared.Application.Annotation.Validators.UniqueListValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueListValidator.class)
public @interface UniqueList {
    String message() default "One or more values already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Unique[] value();
}
