package com.e_com.AuthService.Controller;

import com.e_com.AuthService.Annotation.Auth.Authenticated;
import com.e_com.AuthService.Constants.Http;
import com.e_com.AuthService.Contract.IAuthService;
import com.e_com.AuthService.Model.User;
import com.e_com.AuthService.Response.AuthResponse;
import com.e_com.AuthService.Response.ProfileResponse;
import com.e_com.AuthService.Response.RegisterResponse;
import com.e_com.AuthService.Utils.Auth.ContextHolder;
import com.e_com.AuthService.Validation.*;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService auth;

    private ResponseEntity<HashMap<String, Object>> createAuthResponse(AuthResponse authRes) {
        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", authRes.getAccessToken())
                .httpOnly(true) // JS không truy cập
                .path("/") // scope cookie
                .maxAge(15 * 60) // 15 phút
                .sameSite("Strict") // CSRF protection
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", authRes.getRefreshToken())
                .httpOnly(true)
                // .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 ngày
                .sameSite("Strict")
                .build();

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", authRes.getSuccess());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody(required = false) RegisterRequest req)
            throws MessagingException {
        return auth.register(req);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@Valid @RequestBody(required = false) LoginRequest req) {
        var authRes = auth.login(req);
        return createAuthResponse(authRes);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<HashMap<String, Object>> refreshToken(@Valid @RequestBody(required = false) RefreshTokenRequest req) {
        var authRes = auth.refreshToken(req);
        return createAuthResponse(authRes);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<HashMap<String, Object>> activeUser(@Valid @ModelAttribute ActivateUserRequest request) {
        var authRes = auth.activeUser(request.getEmail(), request.getToken());
        return createAuthResponse(authRes);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody(required = false) ForgotPasswordRequest req)
            throws MessagingException {
        boolean sent = auth.sendResetPasswordEmail(req.getEmail());
        if (sent) {
            return "A password reset link has been sent to your email address.";
        } else {
            return "Failed to send password reset email. Please try again later.";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @RequestBody(required = false) ResetPasswordRequest req) {
        boolean reset = auth.resetPassword(req.getEmail(), req.getToken(), req.getNewPassword());
        if (reset) {
            return "Your password has been reset successfully.";
        } else {
            return "Invalid or expired password reset link.";
        }
    }

    @Authenticated
    @GetMapping("/profile")
    public ProfileResponse profile() {
        User user = ContextHolder.getUser();
        return new ProfileResponse(user);
    }

    @Authenticated
    @PostMapping("/logout")
    public ResponseEntity<HashMap<String, Object>> logout(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ACCESS_TOKEN".equals(cookie.getName())) {
                    accessToken = cookie.getValue().trim();
                } else if ("REFRESH_TOKEN".equals(cookie.getName())) {
                    refreshToken = cookie.getValue().trim();
                }

                if (accessToken != null && refreshToken != null) {
                    break;
                }
            }
        }

        auth.logout(Http.ACCESS_TOKEN_COOKIE, accessToken);
        auth.logout(Http.REFRESH_TOKEN_COOKIE, refreshToken);

        AuthResponse authResponse = new AuthResponse(null, null, true);

        return createAuthResponse(authResponse);
    }
}
