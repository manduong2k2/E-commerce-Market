package com.e_com.StorageService.Utils.Auth;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e_com.StorageService.Constants.ErrorMessage;

import org.springframework.security.core.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    private final PublicKey publicKey;
    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    public JwtService(
        @Value("${jwt.public-key}") Resource publicKeyResource) {
        this.publicKey = loadPublicKey(publicKeyResource);
    }

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private PublicKey loadPublicKey(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes())
                    .replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(key);
            return KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    public Claims verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            
            if(blacklist.containsKey(claims.getBody().getId())) {
                throw new AuthenticationException(ErrorMessage.TOKEN_BLACKLISTED) {
                };
            }

            return claims.getBody();
        } catch (JwtException e) {
            throw new AuthenticationException(ErrorMessage.TOKEN_INVALID) {};
        }
    }

    public void invalidateToken(String token) {
        Claims claims = verifyToken(token);
        String jti = claims.getId();
        Date exp = claims.getExpiration();

        long ttl = exp.getTime() - System.currentTimeMillis();

        blacklist.put(jti, System.currentTimeMillis() + ttl);
    }
}
