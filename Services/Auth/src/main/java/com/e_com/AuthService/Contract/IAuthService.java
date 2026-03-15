package com.e_com.AuthService.Contract;
import com.e_com.AuthService.Response.AuthResponse;
import com.e_com.AuthService.Response.RegisterResponse; 
import com.e_com.AuthService.Validation.*;

public interface IAuthService {
    public RegisterResponse register(RegisterRequest req);
    public AuthResponse activeUser(String email, String token);
    public AuthResponse login(LoginRequest req);
    public AuthResponse refreshToken(RefreshTokenRequest req);
    public boolean sendActivationEmail(String email);
    public boolean sendResetPasswordEmail(String email);
    public boolean resetPassword(String email, String token, String newPassword);
    public void logout(String key, String token);
}
