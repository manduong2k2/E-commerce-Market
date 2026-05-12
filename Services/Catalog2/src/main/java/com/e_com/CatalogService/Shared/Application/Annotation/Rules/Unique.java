package com.e_com.CatalogService.Shared.Application.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.e_com.CatalogService.Shared.Application.Annotation.Validators.UniqueValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Repeatable(UniqueList.class)
public @interface Unique {

    String message() default "Value already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String table();
    String column();

    // optional: hỗ trợ soft delete
    String deletedAtColumn() default "";
    
    // optional: điều kiện bổ sung (ví dụ: "role_id = 1 AND status = 'active'")
    String whereClause() default "";
}
