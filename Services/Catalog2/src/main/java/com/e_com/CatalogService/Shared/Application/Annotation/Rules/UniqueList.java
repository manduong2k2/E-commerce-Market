package com.e_com.CatalogService.Shared.Application.Annotation.Rules;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueList {
    Unique[] value();
}
