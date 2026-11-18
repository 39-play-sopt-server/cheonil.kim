package org.sopt.repository;

import org.sopt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    @Modifying
    @Query("delete from RefreshToken rt where rt.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
