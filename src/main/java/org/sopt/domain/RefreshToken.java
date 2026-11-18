package org.sopt.domain;

import jakarta.persistence.*;

import java.time.Instant;

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

    private RefreshToken(Long memberId, String tokenId, Instant expiresAt){
        this.memberId = memberId;
        this.tokenId = tokenId;
        this.expiresAt = expiresAt;
    }

    public static RefreshToken create(Long memberId, String tokenId, Instant expiresAt){
        return new RefreshToken(memberId, tokenId, expiresAt);
    }

    public void rotate(String tokenId, Instant expiresAt){
        this.tokenId = tokenId;
        this.expiresAt = expiresAt;
    }

    public boolean matches(String tokenId){
        return this.tokenId.equals(tokenId);
    }
}
