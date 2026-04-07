package com.e_com.AuthService.Service;

import com.e_com.AuthService.Constants.ErrorMessage;
import com.e_com.AuthService.Constants.Queue;
import com.e_com.AuthService.Constants.UserStatus;
import com.e_com.AuthService.Contract.IAuthService;
import com.e_com.AuthService.Entity.Role;
import com.e_com.AuthService.Model.User;
import com.e_com.AuthService.Repository.IRoleRepository;
import com.e_com.AuthService.Repository.IUserRepository;
import com.e_com.AuthService.Response.AuthResponse;
import com.e_com.AuthService.Response.RegisterResponse;
import com.e_com.AuthService.Utils.Auth.JwtService;
import com.e_com.AuthService.Validation.*;

import jakarta.mail.MessagingException;
import lombok.experimental.var;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private MessagePublisher messagePublisher;

    @Transactional
    public RegisterResponse register(RegisterRequest req) throws MessagingException {
        var user = new User(null, req.getEmail(), encoder.encode(req.getPassword()), LocalDateTime.now());
        var userEntity = user.toEntity();
        var clientRole = this.roleRepo.findByCode("CUSTOMER");
        var roles = new java.util.HashSet<Role>();
        roles.add(clientRole);
        userEntity.setRoles(roles);
        this.repo.save(userEntity);
        sendActivationEmail(user.getEmail());

        return new RegisterResponse(true,
                "An email has been sent to your email address. Please verify your email to activate your account.");
    }

    public AuthResponse activeUser(String email, String token) {
        var user = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));

        boolean isVerified = emailVerificationTokenService.verify(email, token);
        if (!isVerified) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.TOKEN_INVALID);
        }

        user.setStatus("ACTIVE");
        repo.save(user);
        return new AuthResponse(tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public AuthResponse login(LoginRequest req) {
        var user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new AuthenticationException(ErrorMessage.CREDENTIALS) {});

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new AuthenticationException(ErrorMessage.ACTIVATION) {};
        }

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new AuthenticationException(ErrorMessage.CREDENTIALS) {};
        }

        return new AuthResponse(
                tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public AuthResponse refreshToken(RefreshTokenRequest req) {
        var claims = tokenService.verifyToken(req.getRefreshToken());
        if (claims == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.TOKEN_INVALID);

        var user = repo.findById(UUID.fromString(claims.getSubject()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));

        return new AuthResponse(tokenService.generateAccessToken(user.toDomain()),
                tokenService.generateRefreshToken(user.toDomain()),
                true);
    }

    public boolean sendActivationEmail(String email) throws MessagingException {
        String token = UUID.randomUUID().toString(); // switch

        emailVerificationTokenService.createToken(email, token);
        emailVerificationTokenService.sendVerifyEmail(email, token);
        return true;
    }

    public boolean sendResetPasswordEmail(String email) throws MessagingException {
        String token = UUID.randomUUID().toString();

        emailVerificationTokenService.createToken(email, token);
        emailVerificationTokenService.sendResetPasswordEmail(email, token);
        return true;
    }

    public boolean resetPassword(String email, String token, String newPassword) {
        var user = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));

        boolean isVerified = emailVerificationTokenService.verify(email, token);
        if (!isVerified) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.TOKEN_INVALID);
        }

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);
        return true;
    }

    public void logout(String key, String token) {
        tokenService.invalidateToken(token);

        var message = String.format(
                "{\"action\":\"logout\",\"token\":\"%s\",\"key\":\"%s\",\"timestamp\":\"%s\"}",
                token,
                key,
                java.time.Instant.now().toString());

        messagePublisher.sendMessage(message, Queue.LOGOUT);
    }
}
