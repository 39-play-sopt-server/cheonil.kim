package org.sopt.dto.response;

import java.util.List;

public record PostListResponse(
        List<PostResponse> posts,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
