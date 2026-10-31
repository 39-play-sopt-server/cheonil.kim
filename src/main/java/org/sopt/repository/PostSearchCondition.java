package org.sopt.repository;

import org.sopt.domain.PostCategory;

public record PostSearchCondition(
        String title,
        PostCategory category,
        Long memberId
) {
}
