package org.sopt.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.response.MemberResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "회원 관리 API")
public interface MemberControllerDocs {
    @Operation(summary = "내 정보 조회", description = "Access Token의 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회에 성공했습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    ResponseEntity<BaseResponse<MemberResponse>> getMyInfo(@Parameter(hidden = true) Long memberId);
}
