package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(example = "sopt@sopt.org") @NotBlank(message = "이메일은 비어있을 수 없습니다.")
        String email,

        @Schema(example = "password123") @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
        String password) {}
