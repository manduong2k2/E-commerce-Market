package com.e_com.AuthService.Contract;
import com.e_com.AuthService.Response.AuthResponse;
import com.e_com.AuthService.Response.RegisterResponse; 
import com.e_com.AuthService.Validation.*;

import jakarta.mail.MessagingException;

public interface IAuthService {
    public RegisterResponse register(RegisterRequest req) throws MessagingException;
    public AuthResponse activeUser(String email, String token);
    public AuthResponse login(LoginRequest req);
    public AuthResponse refreshToken(RefreshTokenRequest req);
    public boolean sendActivationEmail(String email) throws MessagingException;
    public boolean sendResetPasswordEmail(String email) throws MessagingException;
    public boolean resetPassword(String email, String token, String newPassword);
    public void logout(String key, String token);
}
