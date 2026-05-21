package com.e_com.CatalogService.Shared.Infrastructure.Utils.Auth;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e_com.CatalogService.Shared.Domain.Constants.ErrorMessage;

import org.springframework.security.core.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    private final PublicKey publicKey;
    private final StringRedisTemplate redisTemplate;

    public JwtService(
            @Value("${jwt.public-key}") Resource publicKeyResource,
            StringRedisTemplate redisTemplate) {
        this.publicKey = loadPublicKey(publicKeyResource);
        this.redisTemplate = redisTemplate;
    }

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

            String jti = claims.getBody().getId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + jti))) {
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
        long ttl = claims.getExpiration().getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + jti,
                    "1",
                    Expiration.milliseconds(ttl)
                );
        }
    }
}