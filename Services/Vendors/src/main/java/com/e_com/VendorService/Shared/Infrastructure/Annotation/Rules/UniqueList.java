package com.e_com.VendorService.Shared.Infrastructure.Annotation.Rules;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueList {
    Unique[] value();
}
