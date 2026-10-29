package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.code.PostSuccessCode;
import org.sopt.controller.docs.PostControllerDocs;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.PostCreateRequest;
import org.sopt.dto.request.PostUpdateRequest;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/posts")
public class PostController implements PostControllerDocs {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(
            @Valid @RequestBody PostCreateRequest request
    ){
        postService.createPost(request);
        return ResponseEntity
                .status(PostSuccessCode.POST_CREATED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_CREATED));
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<PostListResponse>> getPostList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "memberId", required = false) Long memberId
    ){
        return ResponseEntity
                .status(PostSuccessCode.POST_READ.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_READ,
                        postService.getPostList(page, size, category, title, memberId)));
    }

    @Override
    @GetMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable(name = "postId") Long id
    ){
        return ResponseEntity
                .status(PostSuccessCode.POST_READ.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_READ, postService.getPost(id)));

    }

    @Override
    @PutMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable(name="postId") Long id,
            @Valid @RequestBody PostUpdateRequest request
    ){
        postService.updatePost(id, request);
        return ResponseEntity
                .status(PostSuccessCode.POST_UPDATED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_UPDATED));
    }

    @Override
    @DeleteMapping(path="/{postId}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable(name="postId") Long id
    ){
        postService.deletePost(id);
        return ResponseEntity
                .status(PostSuccessCode.POST_DELETED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_DELETED));
    }
}
