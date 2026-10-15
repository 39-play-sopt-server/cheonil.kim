package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
        @Schema(description = "수정할 제목", example = "수정된 제목")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        String title,

        @Schema(description = "수정할 내용", example = "수정된 내용")
        @NotBlank(message = "내용은 비어있을 수 없습니다.")
        String content
) {}
