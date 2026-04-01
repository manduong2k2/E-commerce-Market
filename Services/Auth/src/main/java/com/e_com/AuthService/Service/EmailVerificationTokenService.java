package com.e_com.AuthService.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_com.AuthService.Embeddable.EmailVerifyTokenId;
import com.e_com.AuthService.Entity.EmailVerifyToken;
import com.e_com.AuthService.Repository.IEmailVerifyTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenService {
    @Autowired
    private IEmailVerifyTokenRepository repo;

    @Autowired
    public PasswordEncoder encoder;

    @Transactional
    public void createToken(String email, String token) {
        String hashed = encoder.encode(token);
        EmailVerifyToken entity = new EmailVerifyToken();
        entity.setEmail(email);
        entity.setToken(hashed);
        entity.setExpiresAt(LocalDateTime.now().plusHours(24));
        repo.save(entity);
    }

    public boolean verify(String email, String token) {
        EmailVerifyToken entity = repo.findByEmail(email);
        if (entity == null) {
            return false;
        }

        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            repo.delete(entity);
            return false;
        }

        if (!encoder.matches(token, entity.getToken())) {
            return false;
        }

        repo.delete(entity);
        return true;
    }

    private final JavaMailSender mailSender;

    @Async
    public void sendVerifyEmail(String toEmail, String token) {

        String link = "http://localhost:8080/api/auth/verify-email?email="
                + toEmail + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Click here to verify your email: " + link);

        mailSender.send(message);
    }

    @Async
    public void sendResetPasswordEmail(String toEmail, String token) {

        String link = "http://localhost:8080/api/auth/reset-password?email="
                + toEmail + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset your password");
        message.setText("Click here to reset your password: " + link);

        mailSender.send(message);
    }
}
