package org.sopt.jwt;

public interface TokenParser {
    TokenClaims parse(String token);
}
