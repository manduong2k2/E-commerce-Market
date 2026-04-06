package com.e_com.AuthService.Contract;

import org.springframework.http.HttpHeaders;

public interface ICookieService {
    HttpHeaders createAuthCookies(String accessToken, String refreshToken);
    
    HttpHeaders createClearCookies();
}
