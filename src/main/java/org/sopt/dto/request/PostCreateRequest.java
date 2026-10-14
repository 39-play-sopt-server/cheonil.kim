package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest (
    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    String title,

    @NotBlank(message = "카테고리는 비어있을 수 없습니다.")
    String category,

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    String content
) {}
