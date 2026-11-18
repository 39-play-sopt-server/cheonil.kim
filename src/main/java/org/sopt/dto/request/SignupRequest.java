package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Schema(example = "sopt@sopt.org")
        @NotBlank(message = "이메일은 비어있을 수 없습니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Schema(example = "김솝트")
        @NotBlank(message = "이름은 비어있을 수 없습니다.")
        String name,

        @Schema(example = "password123")
        @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
        @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하여야 합니다.")
        String password) {}
