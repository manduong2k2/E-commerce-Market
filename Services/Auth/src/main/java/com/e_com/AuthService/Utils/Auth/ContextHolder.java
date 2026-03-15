package com.e_com.AuthService.Utils.Auth;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.e_com.AuthService.Model.User;
import com.e_com.AuthService.Repository.IUserRepository;

@Component
public class ContextHolder {

    private static IUserRepository userRepository;

    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public ContextHolder(IUserRepository repo) {
        userRepository = repo;
    }

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

        return userRepository.findById(UUID.fromString(userId)).orElse(null).toDomain();
    }

    public static void clear() {
        currentUser.remove();
    }
}
