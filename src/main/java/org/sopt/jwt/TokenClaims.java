package org.sopt.jwt;

import org.sopt.domain.Role;

import java.time.Instant;

public record TokenClaims(
        Long memberId,
        Role role,
        TokenType type,
        String tokenId,
        Instant expiresAt
) {
}
