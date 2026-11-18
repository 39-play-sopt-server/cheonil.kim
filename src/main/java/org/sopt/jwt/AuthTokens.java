package org.sopt.jwt;

public record AuthTokens(String accessToken, IssuedToken refreshToken) {
}
