package com.e_com.StorageService.Utils.Auth;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.e_com.StorageService.Model.User;

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

        return new User(UUID.fromString(userId), userId, userId, null);
    }

    public static void clear() {
        currentUser.remove();
    }
}
