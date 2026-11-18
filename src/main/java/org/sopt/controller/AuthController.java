package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.code.AuthSuccessCode;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.LoginRequest;
import org.sopt.dto.request.SignupRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.dto.response.TokenResponse;
import org.sopt.jwt.AuthTokens;
import org.sopt.jwt.RefreshTokenCookieManager;
import org.sopt.jwt.TokenClaims;
import org.sopt.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenCookieManager cookieManager;

    public AuthController(AuthService authService, RefreshTokenCookieManager cookieManager) {
        this.authService = authService;
        this.cookieManager = cookieManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<MemberResponse>> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity
                .status(AuthSuccessCode.SIGNUP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AuthSuccessCode.SIGNUP_SUCCESS, authService.signup(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        return withTokens(AuthSuccessCode.LOGIN_SUCCESS, authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> reissue(
            @CookieValue(name = RefreshTokenCookieManager.COOKIE_NAME, required = false) String refreshToken
    ) {
        return withTokens(AuthSuccessCode.REISSUE_SUCCESS, authService.reissue(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(Authentication authentication) {
        authService.logout(memberIdOf(authentication), accessTokenOf(authentication));
        return withExpiredCookie(AuthSuccessCode.LOGOUT_SUCCESS);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<BaseResponse<Void>> withdraw(Authentication authentication) {
        authService.withdraw(memberIdOf(authentication), accessTokenOf(authentication));
        return withExpiredCookie(AuthSuccessCode.WITHDRAW_SUCCESS);
    }

    private ResponseEntity<BaseResponse<TokenResponse>> withTokens(AuthSuccessCode code, AuthTokens tokens) {
        return ResponseEntity
                .status(code.getHttpStatus())
                .header(HttpHeaders.SET_COOKIE, cookieManager.create(tokens.refreshToken()).toString())
                .body(BaseResponse.success(code, new TokenResponse(tokens.accessToken())));
    }

    private ResponseEntity<BaseResponse<Void>> withExpiredCookie(AuthSuccessCode code) {
        return ResponseEntity
                .status(code.getHttpStatus())
                .header(HttpHeaders.SET_COOKIE, cookieManager.expire().toString())
                .body(BaseResponse.success(code));
    }

    // JwtAuthenticationFilter가 principal에 회원 ID, details에 Access Token claim을 담아둠
    private Long memberIdOf(Authentication authentication) {
        return (Long) authentication.getPrincipal();
    }

    private TokenClaims accessTokenOf(Authentication authentication) {
        return (TokenClaims) authentication.getDetails();
    }
}
