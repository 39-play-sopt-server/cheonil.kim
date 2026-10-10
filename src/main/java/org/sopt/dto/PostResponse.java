package org.sopt.dto;

import org.sopt.domain.Post;

public record PostResponse(
        int id,
        String title,
        String category,
        String writtenDate,
        String content
) {
    public static PostResponse from(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getCategory().getDisplayName(),
                post.getWrittenDate().toString(),
                post.getContent()
        );
    }
}
