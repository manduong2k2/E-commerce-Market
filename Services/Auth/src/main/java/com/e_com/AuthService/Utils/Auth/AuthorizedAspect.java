package com.e_com.AuthService.Utils.Auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AuthorizedAspect {

    private final HttpServletRequest request;

    private final JwtService jwtService;

    public AuthorizedAspect(HttpServletRequest request, JwtService jwtService) {
        this.request = request;
        this.jwtService = jwtService;
    }

    @Around("@annotation(com.e_com.AuthService.Annotation.Auth.Authenticated)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ACCESS_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            throw new AuthenticationException("Unauthenticated"){}; 
        }

        String userId = jwtService.verifyToken(token).getSubject();
        if (userId == null) {
            throw new AuthenticationException("Unauthenticated"){};
        }

        ContextHolder.setUser(userId);

        try {
            return joinPoint.proceed();
        } finally {
            ContextHolder.clear();
        }
    }
}