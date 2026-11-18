package org.sopt.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.code.AuthErrorCode;
import org.sopt.code.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 인증이 필요한 API에 인증 정보 없이 접근했을 때 (401)
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final SecurityErrorResponseWriter errorResponseWriter;

    public JwtAuthenticationEntryPoint(SecurityErrorResponseWriter errorResponseWriter){
        this.errorResponseWriter = errorResponseWriter;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        ErrorCode errorCode = request.getAttribute(JwtAuthenticationFilter.AUTH_ERROR_ATTRIBUTE) instanceof ErrorCode code
                ? code
                : AuthErrorCode.UNAUTHENTICATED;
        errorResponseWriter.write(response, errorCode);
    }
}
