package com.e_com.VendorService.Shared.Infrastructure.Utils.Auth;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.e_com.VendorService.Shared.Infrastructure.Annotation.Auth.Authorized;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizedAspect {

    private final ApplicationContext applicationContext;
    private final HttpServletRequest request;

    @Before("@annotation(authorized)")
    public void handle(JoinPoint joinPoint, Authorized authorized) {
        try {
            Class<?> securityClass = authorized.security();
            String methodName = authorized.method();

            Object bean = applicationContext.getBean(securityClass);

            Method method = securityClass.getMethod(methodName, HttpServletRequest.class);

            method.invoke(bean, request);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Security method not found");
        } catch (Exception e) {
            throw new RuntimeException("Authorization failed", e);
        }
    }
}