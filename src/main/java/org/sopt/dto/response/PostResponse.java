package org.sopt.dto.response;

import org.sopt.domain.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String category,
        String writerName,
        LocalDateTime createdAt,
        String content,
        int viewCount,
        int likeCount
) {
    public static PostResponse from(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getCategory().getDisplayName(),
                post.getMember().getName(),
                post.getCreatedAt(),
                post.getContent(),
                post.getViewCount(),
                post.getLikeCount()
        );
    }
}
