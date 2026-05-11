package com.e_com.Auth.Domain.Contract;
import com.e_com.Auth.Application.DTO.Request.LoginRequest;
import com.e_com.Auth.Application.DTO.Request.RefreshTokenRequest;
import com.e_com.Auth.Application.DTO.Request.RegisterRequest;
import com.e_com.Auth.Application.DTO.Response.AuthResponse;
import com.e_com.Auth.Application.DTO.Response.RegisterResponse;

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
