package com.e_com.AuthService.Entity;

import com.e_com.AuthService.Embeddable.EmailVerifyTokenId;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_verify_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifyToken {
    @EmbeddedId
    private EmailVerifyTokenId id;
}