package org.sopt.jwt;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class CaffeineTokenBlacklist implements TokenBlacklist {

    // 크기 제한(maximumSize)은 일부러 두지 않음.
    // 용량 초과로 항목이 밀려나면 로그아웃한 토큰이 다시 유효해지기 때문.
    // 항목마다 토큰의 만료 시각에 지워지므로 최대 Access Token 수명만큼의 로그아웃 건수만 유지됨.
    private final Cache<String, Instant> cache = Caffeine.newBuilder()
            .expireAfter(new Expiry<String, Instant>() {
                @Override
                public long expireAfterCreate(String tokenId, Instant expiresAt, long currentTime) {
                    return Math.max(0, Duration.between(Instant.now(), expiresAt).toNanos());
                }

                @Override
                public long expireAfterUpdate(String tokenId, Instant expiresAt, long currentTime, long currentDuration) {
                    return currentDuration;
                }

                @Override
                public long expireAfterRead(String tokenId, Instant expiresAt, long currentTime, long currentDuration) {
                    return currentDuration;
                }
            })
            .build();

    @Override
    public void add(String tokenId, Instant expiresAt) {
        cache.put(tokenId, expiresAt);
    }

    @Override
    public boolean contains(String tokenId) {
        return cache.getIfPresent(tokenId) != null;
    }
}
