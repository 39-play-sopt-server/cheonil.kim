package org.sopt.repository;

import org.sopt.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    @Modifying
    @Query("delete from PostLike pl where pl.member.id = :memberId and pl.post.id = :postId")
    void deleteByMemberIdAndPostId(@Param("memberId") Long memberId,
                                   @Param("postId") Long postId);
}
