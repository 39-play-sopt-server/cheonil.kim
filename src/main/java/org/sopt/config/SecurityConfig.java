package org.sopt.config;

import org.sopt.jwt.JwtAuthenticationEntryPoint;
import org.sopt.jwt.JwtAuthenticationFilter;
import org.sopt.jwt.TokenBlacklist;
import org.sopt.jwt.TokenParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenParser tokenParser;
    private final TokenBlacklist tokenBlacklist;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(TokenParser tokenParser,
                          TokenBlacklist tokenBlacklist,
                          JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.tokenParser=tokenParser;
        this.tokenBlacklist=tokenBlacklist;
        this.authenticationEntryPoint=authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 STATELESS
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login", "/api/v1/auth/reissue").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts", "/api/v1/posts/*").permitAll()
                        .anyRequest().authenticated())
                // 인증 실패 시 401 응답
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint))
                // JWT 인증 필터 추가
                .addFilterBefore(new JwtAuthenticationFilter(tokenParser, tokenBlacklist),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}