package com.e_com.AuthService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.AuthService.Embeddable.EmailVerifyTokenId;
import com.e_com.AuthService.Entity.EmailVerifyToken;

public interface IEmailVerifyTokenRepository extends JpaRepository<EmailVerifyToken, EmailVerifyTokenId> {
    public EmailVerifyToken findByEmail(String email);
}
