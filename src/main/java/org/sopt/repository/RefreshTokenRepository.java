package org.sopt.repository;

import org.sopt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    @Modifying
    @Query("delete from RefreshToken rt where rt.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);

    // 로그인: 회원당 한 행을 원자적으로 추가 또는 교체.
    // 조회 후 save하면 같은 계정으로 동시에 처음 로그인할 때 unique 제약 위반(500)이 남
    @Modifying
    @Query(value = """
            insert into refresh_token (member_id, token_id, expires_at)
            values (:memberId, :tokenId, :expiresAt) as new_token
            on duplicate key update token_id = new_token.token_id, expires_at = new_token.expires_at
            """, nativeQuery = true)
    void upsert(@Param("memberId") Long memberId,
                @Param("tokenId") String tokenId,
                @Param("expiresAt") Instant expiresAt);

    // 재발급: 저장된 jti가 요청에 쓰인 jti일 때만 교체.
    // 같은 Refresh로 동시에 재발급하면 먼저 커밋한 요청만 1을 받고, 나머지는 0을 받음
    @Modifying
    @Query("update RefreshToken rt set rt.tokenId = :newTokenId, rt.expiresAt = :expiresAt " +
           "where rt.memberId = :memberId and rt.tokenId = :oldTokenId")
    int rotateIfMatches(@Param("memberId") Long memberId,
                        @Param("oldTokenId") String oldTokenId,
                        @Param("newTokenId") String newTokenId,
                        @Param("expiresAt") Instant expiresAt);
}
