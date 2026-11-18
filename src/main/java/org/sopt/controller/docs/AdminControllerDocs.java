package org.sopt.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.response.MemberResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "관리자", description = "ROLE_ADMIN 전용 API")
public interface AdminControllerDocs {
    @Operation(summary = "전체 회원 목록 조회", description = "모든 회원을 가입 순으로 조회합니다. 관리자만 호출할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 목록 조회에 성공했습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.")
    })
    ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers();
}
