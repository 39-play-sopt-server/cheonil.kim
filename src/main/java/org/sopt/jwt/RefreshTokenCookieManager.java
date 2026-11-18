package org.sopt.jwt;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class RefreshTokenCookieManager {
    public static final String COOKIE_NAME = "refreshToken";
    private static final String COOKIE_PATH = "/api/v1/auth";

    public ResponseCookie create(IssuedToken refreshToken){
        return baseCookie(refreshToken.value())
                .maxAge(Duration.between(Instant.now(), refreshToken.expiresAt()))
                .build();
    }

    public ResponseCookie expire(){
        return baseCookie("").maxAge(0).build();
    }

    private ResponseCookie.ResponseCookieBuilder baseCookie(String value){
        return ResponseCookie.from(COOKIE_NAME, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(COOKIE_PATH);
    }
}
