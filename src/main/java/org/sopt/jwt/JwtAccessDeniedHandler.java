package org.sopt.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.code.AuthErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인증은 됐지만 필요한 권한(ROLE_ADMIN 등)이 없을 때 (403)
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final SecurityErrorResponseWriter errorResponseWriter;

    public JwtAccessDeniedHandler(SecurityErrorResponseWriter errorResponseWriter) {
        this.errorResponseWriter = errorResponseWriter;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        errorResponseWriter.write(response, AuthErrorCode.ACCESS_DENIED);
    }
}
