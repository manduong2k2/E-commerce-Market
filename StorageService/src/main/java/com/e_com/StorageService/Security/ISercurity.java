package com.e_com.StorageService.Security;

import jakarta.servlet.http.HttpServletRequest;

public interface ISercurity {
    void authorize(HttpServletRequest request);
}
