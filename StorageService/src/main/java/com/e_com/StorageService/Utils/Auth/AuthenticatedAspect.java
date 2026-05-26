package com.e_com.StorageService.Utils.Auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.e_com.StorageService.Constants.ErrorMessage;
import com.e_com.StorageService.Constants.Http;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AuthenticatedAspect {

    private final HttpServletRequest request;

    private final JwtService jwtService;

    public AuthenticatedAspect(HttpServletRequest request, JwtService jwtService) {
        this.request = request;
        this.jwtService = jwtService;
    }

    @Around("@annotation(com.e_com.StorageService.Annotation.Auth.Authenticated)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Http.ACCESS_TOKEN_COOKIE.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            throw new AuthenticationException(ErrorMessage.UNAUTHENTICATED){}; 
        }

        String userId = jwtService.verifyToken(token).getSubject();
        if (userId == null) {
            throw new AuthenticationException(ErrorMessage.UNAUTHENTICATED){};
        }

        ContextHolder.setUser(userId);

        try {
            return joinPoint.proceed();
        } finally {
            ContextHolder.clear();
        }
    }
}