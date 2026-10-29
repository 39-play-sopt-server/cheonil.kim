package org.sopt.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.PostCreateRequest;
import org.sopt.dto.request.PostUpdateRequest;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시글", description = "게시글 CRUD API")
public interface PostControllerDocs {

    @Operation(summary = "게시글 생성", description = "제목, 카테고리, 내용을 입력받아 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글이 작성되었습니다."),
            @ApiResponse(responseCode = "400", description = "비어있거나 부적절한 입력")
    })
    ResponseEntity<BaseResponse<Void>> createPost(
            PostCreateRequest request
    );

    @Operation(summary = "게시글 상세 조회", description = "게시글 id로 단건 조회합니다. 호출 시 조회수가 1 증가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1") Long id
    );

    @Operation(summary = "게시글 목록 조회",
            description = "게시글 목록을 최신순으로 조회합니다. 제목(앞부분 일치), 카테고리, 작성자 ID로 필터링할 수 있으며 조건은 자유롭게 조합됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "부적절한 페이지 파라미터")
    })
    ResponseEntity<BaseResponse<PostListResponse>> getPostList(
            @Parameter(description = "페이지 번호", example = "1") int page,
            @Parameter(description = "페이지 크기", example = "10") int size,
            @Parameter(description = "카테고리 종류", example = "REVIEW") String category,
            @Parameter(description = "제목 검색어 (앞부분 일치)", example = "첫") String title,
            @Parameter(description = "작성자 ID", example = "1") Long memberId
    );

    @Operation(summary = "게시글 수정", description = "특정 게시글의 제목과 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글이 수정되었습니다."),
            @ApiResponse(responseCode = "400", description = "비어있거나 부적절한 입력"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "게시글 ID", example = "1") Long id,
            PostUpdateRequest request
    );

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글이 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "게시글 ID", example = "1") Long id
    );

}
