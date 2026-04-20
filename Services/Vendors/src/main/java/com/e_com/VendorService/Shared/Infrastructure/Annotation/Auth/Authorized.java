package com.e_com.VendorService.Shared.Infrastructure.Annotation.Auth;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorized {
    Class<?> security();
    String method();
}
