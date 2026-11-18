package org.sopt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sopt.code.AuthErrorCode;
import org.sopt.domain.Role;
import org.sopt.exception.AuthException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider implements TokenIssuer, TokenParser{
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";

    private final SecretKey secretKey;
    private final JwtProperties properties;

    public JwtTokenProvider(JwtProperties properties){
        this.secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
        this.properties=properties;
    }

    @Override
    public IssuedToken issue(Long memberId, Role role, TokenType type){
        Instant now = Instant.now();
        Instant expiresAt = now.plus(expirationOf(type));
        String tokenId = UUID.randomUUID().toString();

        String value = Jwts.builder()
                .id(tokenId)
                .subject(String.valueOf(memberId))
                .claim(CLAIM_ROLE, role.name())
                .claim(CLAIM_TYPE, type.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();

        return new IssuedToken(value, tokenId, expiresAt);
    }

    @Override
    public TokenClaims parse(String token){
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new TokenClaims(
                    Long.valueOf(claims.getSubject()),
                    Role.valueOf(claims.get(CLAIM_ROLE, String.class)),
                    TokenType.valueOf(claims.get(CLAIM_TYPE, String.class)),
                    claims.getId(),
                    claims.getExpiration().toInstant()
            );
        } catch (ExpiredJwtException e){
            throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException | NullPointerException e){
            // 서명 불일치, 형식 오류, 필수 claims 누락
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    private Duration expirationOf(TokenType type){
        return switch (type){
            case ACCESS -> properties.accessTokenExpiration();
            case REFRESH -> properties.refreshTokenExpiration();
        };
    }
}
