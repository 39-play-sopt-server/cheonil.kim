package org.sopt.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.MemberCreateRequest;
import org.sopt.dto.response.MemberResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "회원 관리 API")
public interface MemberControllerDocs {
    @Operation(summary = "회원 생성", description = "이메일과 이름을 입력받아 회원을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원이 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "비어있거나 부적절한 입력"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일입니다.")
    })
    ResponseEntity<BaseResponse<MemberResponse>> createMember(MemberCreateRequest request);

    @Operation(summary = "회원 삭제", description = "회원 id로 회원을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원이 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원입니다.")
    })
    ResponseEntity<BaseResponse<Void>> deleteMember(
            @Parameter(description = "회원 ID", example = "1") Long id
    );
}
