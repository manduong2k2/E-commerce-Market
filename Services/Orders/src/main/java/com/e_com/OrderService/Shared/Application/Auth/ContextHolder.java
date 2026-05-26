package com.e_com.OrderService.Shared.Application.Auth;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.e_com.OrderService.Shared.Domain.Auth.User;

@Component
public class ContextHolder {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static void setUser(String userId) {
        currentUser.set(userId);
    }

    public static String getUserId() {
        return currentUser.get();
    }

    public static User getUser() {
        String userId = getUserId();
        if (userId == null) {
            return null;
        }

        return new User(UUID.fromString(userId));
    }

    public static void clear() {
        currentUser.remove();
    }
}
