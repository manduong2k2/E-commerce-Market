package com.e_com.AuthService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
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

    @Transactional
    public void createToken(String email, String token) {
        EmailVerifyToken entity = new EmailVerifyToken(
                new EmailVerifyTokenId(email, token));
        repo.save(entity);
    }

    public boolean verify(String email, String token) {
        EmailVerifyTokenId id = new EmailVerifyTokenId(email, token);

        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
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
