package com.e_com.VendorService.Shared.Infrastructure.Constants;

public final class ErrorMessage {
    public static final String UNAUTHENTICATED = "Unauthenticated";
    public static final String CREDENTIALS = "Invalid credentials";
    public static final String ACTIVATION = "Account is not active";
    public static final String USER_NOT_FOUND = "User not found";

    public static final String TOKEN_INVALID = "Invalid token";
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String TOKEN_BLACKLISTED = "Token has been blacklisted";
    
    private ErrorMessage() {
        throw new AssertionError("Utility class");
    }
}
