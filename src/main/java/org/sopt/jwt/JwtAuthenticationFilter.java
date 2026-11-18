package org.sopt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.code.AuthErrorCode;
import org.sopt.exception.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_ERROR_ATTRIBUTE = "authError";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenParser tokenParser;
    private final TokenBlacklist tokenBlacklist;

    public JwtAuthenticationFilter(TokenParser tokenParser, TokenBlacklist tokenBlacklist) {
        this.tokenParser = tokenParser;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                TokenClaims claims = tokenParser.parse(token);
                if(claims.type() != TokenType.ACCESS){
                    throw new AuthException(AuthErrorCode.INVALID_TOKEN_TYPE);
                }
                if (tokenBlacklist.contains(claims.tokenId())) {
                    throw new AuthException(AuthErrorCode.REVOKED_TOKEN);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        claims.memberId(),
                        null,
                        List.of(new SimpleGrantedAuthority(claims.role().authority()))
                );
                // 로그아웃, 탈퇴 시 현재 토큰의 jti와 만료 시각을 꺼낼 수 있도록 claim을 함께 보관
                authentication.setDetails(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthException e) {
                request.setAttribute(AUTH_ERROR_ATTRIBUTE, e.getErrorCode());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}