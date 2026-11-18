package org.sopt.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.LoginRequest;
import org.sopt.dto.request.SignupRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(name = "인증", description = "회원가입, 로그인, 토큰 재발급, 로그아웃, 탈퇴 API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입", description = "이메일, 이름, 비밀번호로 가입합니다. 가입한 회원의 권한은 항상 USER입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "비어있거나 부적절한 입력"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일입니다.")
    })
    ResponseEntity<BaseResponse<MemberResponse>> signup(SignupRequest request);

    @Operation(summary = "로그인",
            description = "Access Token은 응답 body로, Refresh Token은 HttpOnly 쿠키(Path=/api/v1/auth)로 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인에 성공했습니다."),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호가 올바르지 않습니다.")
    })
    ResponseEntity<BaseResponse<TokenResponse>> login(LoginRequest request);

    @Operation(summary = "토큰 재발급",
            description = "Refresh Token 쿠키로 Access Token과 Refresh Token을 모두 새로 발급합니다. "
                    + "이미 교체된 Refresh Token이 다시 사용되면 탈취로 보고 해당 회원의 Refresh Token을 무효화합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰이 재발급되었습니다."),
            @ApiResponse(responseCode = "401", description = "리프레시 토큰이 없거나 만료, 위조, 재사용된 토큰")
    })
    ResponseEntity<BaseResponse<TokenResponse>> reissue(@Parameter(hidden = true) String refreshToken);

    @Operation(summary = "로그아웃",
            description = "Refresh Token을 삭제하고, 현재 Access Token을 만료 시각까지 블랙리스트에 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    ResponseEntity<BaseResponse<Void>> logout(@Parameter(hidden = true) Authentication authentication);

    @Operation(summary = "회원 탈퇴",
            description = "작성한 게시글과 누른 좋아요를 정리하고 회원을 삭제합니다. 현재 Access Token도 블랙리스트에 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴가 완료되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    ResponseEntity<BaseResponse<Void>> withdraw(@Parameter(hidden = true) Authentication authentication);
}
