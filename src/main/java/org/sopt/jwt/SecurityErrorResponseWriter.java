package org.sopt.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.sopt.code.ErrorCode;
import org.sopt.dto.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// Security 필터 단계의 예외는 @RestControllerAdvice까지 오지 않으므로 같은 BaseResponse 형식으로 직접 작성
@Component
public class SecurityErrorResponseWriter {
    private final JsonMapper jsonMapper;

    public SecurityErrorResponseWriter(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void write(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        jsonMapper.writeValue(response.getWriter(), BaseResponse.fail(errorCode));
    }
}
