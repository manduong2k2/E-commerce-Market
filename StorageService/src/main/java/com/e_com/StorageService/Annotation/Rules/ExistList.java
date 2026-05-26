package com.e_com.StorageService.Annotation.Rules;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistList {
    Exist[] value();
}
