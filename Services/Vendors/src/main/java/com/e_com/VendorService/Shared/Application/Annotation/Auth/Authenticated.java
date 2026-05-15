package com.e_com.VendorService.Shared.Application.Annotation.Auth;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {
}
