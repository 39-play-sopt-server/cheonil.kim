package org.sopt.domain;

import jakarta.persistence.*;

import java.time.Instant;

// 행 추가와 교체는 RefreshTokenRepository의 upsert, rotateIfMatches가 원자적으로 처리
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "member_id", nullable = false, unique = true)
    private Long memberId;

    @Column(name = "token_id", nullable = false)
    private String tokenId;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    protected RefreshToken()  {}

    public boolean matches(String tokenId){
        return this.tokenId.equals(tokenId);
    }
}
