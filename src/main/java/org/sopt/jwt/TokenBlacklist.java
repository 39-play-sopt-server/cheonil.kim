package org.sopt.jwt;

import java.time.Instant;

/**
 * 로그아웃, 탈퇴로 무효화된 Access Token의 jti 저장소.
 * 서버를 여러 대로 늘리면 Redis 구현체로 교체할 수 있도록 인터페이스로 분리.
 */
public interface TokenBlacklist {
    void add(String tokenId, Instant expiresAt);

    boolean contains(String tokenId);
}
