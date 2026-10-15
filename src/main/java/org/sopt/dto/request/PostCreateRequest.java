package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest (
        @Schema(description = "게시글 제목", example = "제목")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        String title,

        @Schema(description = "카테고리", example = "REVIEW", allowableValues = {"QUESTION", "REVIEW", "DIARY", "ETC"})
        @NotBlank(message = "카테고리는 비어있을 수 없습니다.")
        String category,

        @Schema(description = "게시글 내용", example = "내용")
        @NotBlank(message = "내용은 비어있을 수 없습니다.")
        String content
) {}
