package com.e_com.VendorService.Shared.Infrastructure.Annotation.Rules;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
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
    
    // optional: điều kiện bổ sung (ví dụ: "status = 'active' AND role_id = 1")
    String whereClause() default "";
}
