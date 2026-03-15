package com.e_com.AuthService.Service;

import com.e_com.AuthService.Contract.IAuthService;
import com.e_com.AuthService.Entity.Role;
import com.e_com.AuthService.Model.User;
import com.e_com.AuthService.Repository.IRoleRepository;
import com.e_com.AuthService.Repository.IUserRepository;
import com.e_com.AuthService.Response.AuthResponse;
import com.e_com.AuthService.Response.RegisterResponse;
import com.e_com.AuthService.Utils.Auth.JwtService;
import com.e_com.AuthService.Validation.*;

import lombok.experimental.var;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements IAuthService {
    @Autowired
    public IUserRepository repo;

    @Autowired
    public IRoleRepository roleRepo;

    @Autowired
    public JwtService tokenService;

    @Autowired
    public PasswordEncoder encoder;

    @Autowired
    public EmailVerificationTokenService emailVerificationTokenService;

    @Transactional
    public RegisterResponse register(RegisterRequest req) {
        var user = new User(null, req.getEmail(), encoder.encode(req.getPassword()), req.getName(), req.getPhone(),
                "INACTIVE", LocalDateTime.now());
        var userEntity = user.toEntity();
        var clientRole = this.roleRepo.findByCode("CLI_USER");
        var roles = new java.util.HashSet<Role>();
        roles.add(clientRole);
        userEntity.setRoles(roles);
        this.repo.save(userEntity);
        sendActivationEmail(user.getEmail());

        return new RegisterResponse(true,
                "An email has been sent to your email address. Please verify your email to activate your account.");
    }

    public AuthResponse activeUser(String email, String token) {
        boolean verified = emailVerificationTokenService.verify(email, token);
        if (!verified)
            throw new RuntimeException("Invalid verification link");

        var user = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus("ACTIVE");
        repo.save(user);
        return new AuthResponse(tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public AuthResponse login(LoginRequest req) {
        var user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid credentials") {
                });

        if (!user.getStatus().equals("ACTIVE")) {
            throw new AuthenticationException("Account is not active") {
            };
        }

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {
            };
        }

        return new AuthResponse(
                tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public AuthResponse refreshToken(RefreshTokenRequest req) {
        var claims = tokenService.verifyToken(req.getRefreshToken());
        if (claims == null)
            throw new RuntimeException("Invalid refresh token");

        var user = repo.findById(UUID.fromString(claims.getSubject()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public boolean sendActivationEmail(String email) {
        String token = UUID.randomUUID().toString();

        emailVerificationTokenService.createToken(email, token);
        emailVerificationTokenService.sendVerifyEmail(email, token);
        return true;
    }

    public boolean sendResetPasswordEmail(String email) {
        String token = UUID.randomUUID().toString();

        emailVerificationTokenService.createToken(email, token);
        emailVerificationTokenService.sendResetPasswordEmail(email, token);
        return true;
    }

    public boolean resetPassword(String email, String token, String newPassword) {
        var verified = emailVerificationTokenService.verify(email, token);
        if (!verified)
            throw new RuntimeException("Invalid verification link");

        var user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);
        return true;
    }

    public void logout(String key, String token) {
        tokenService.invalidateToken(token);
    }
}
