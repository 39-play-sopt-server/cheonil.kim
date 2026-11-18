package org.sopt.jwt;

import java.time.Instant;

public record IssuedToken(
        String value,
        String tokenId,
        Instant expiresAt
) {
}
