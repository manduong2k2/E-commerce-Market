package com.e_com.CartService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.e_com.CartService.Shared.Application.Annotation.Validators.ExistValidator;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistValidator.class)
@Repeatable(ExistList.class)
public @interface Exist {
    String message() default "Value not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String table();
    String column();

    String deletedAtColumn() default "";
    String whereClause() default "";
}
