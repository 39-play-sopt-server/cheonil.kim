package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
        @Schema(description = "이메일", example = "sopt@sopt.org")
        @NotBlank(message = "이메일은 비어있을 수 없습니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "이름", example = "김천일")
        @NotBlank(message = "이름은 비어있을 수 없습니다.")
        String name
) {
}
