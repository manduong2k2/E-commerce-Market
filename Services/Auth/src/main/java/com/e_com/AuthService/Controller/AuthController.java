package com.e_com.AuthService.Controller;

import com.e_com.AuthService.Annotation.Auth.Authenticated;
import com.e_com.AuthService.Constants.Http;
import com.e_com.AuthService.Contract.IAuthService;
import com.e_com.AuthService.Contract.ICookieService;
import com.e_com.AuthService.Model.User;
import com.e_com.AuthService.Response.ProfileResponse;
import com.e_com.AuthService.Response.RegisterResponse;
import com.e_com.AuthService.Utils.Auth.ContextHolder;
import com.e_com.AuthService.Utils.Auth.JwtService;
import com.e_com.AuthService.Validation.*;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IAuthService auth;

    @Autowired
    private ICookieService cookieService;

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody(required = false) RegisterRequest req)
            throws MessagingException {
        return auth.register(req);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@Valid @RequestBody(required = false) LoginRequest req) {
        var authRes = auth.login(req);
        HttpHeaders cookies = cookieService.createAuthCookies(authRes.getAccessToken(), authRes.getRefreshToken());

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", authRes.getSuccess());

        return ResponseEntity.ok()
                .headers(cookies)
                .body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<HashMap<String, Object>> refreshToken(
            @Valid @RequestBody(required = false) RefreshTokenRequest req) {
        var authRes = auth.refreshToken(req);
        HttpHeaders cookies = cookieService.createAuthCookies(authRes.getAccessToken(), authRes.getRefreshToken());

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", authRes.getSuccess());

        return ResponseEntity.ok()
                .headers(cookies)
                .body(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<HashMap<String, Object>> activeUser(@Valid @ModelAttribute ActivateUserRequest request) {
        var authRes = auth.activeUser(request.getEmail(), request.getToken());
        HttpHeaders cookies = cookieService.createAuthCookies(authRes.getAccessToken(), authRes.getRefreshToken());

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", authRes.getSuccess());

        return ResponseEntity.ok()
                .headers(cookies)
                .body(response);
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
        HashMap<String, String> cookieMap = new HashMap<>();

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }

        String accessToken = cookieMap.get(Http.ACCESS_TOKEN_COOKIE);
        String refreshToken = cookieMap.get(Http.REFRESH_TOKEN_COOKIE);

        jwtService.invalidateToken(accessToken);
        jwtService.invalidateToken(refreshToken);

        HttpHeaders headers = cookieService.createClearCookies();

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }
}
