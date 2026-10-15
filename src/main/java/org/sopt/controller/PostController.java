package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.code.PostSuccessCode;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.request.PostCreateRequest;
import org.sopt.dto.request.PostUpdateRequest;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @Valid @RequestBody PostCreateRequest request
            ){
        postService.createPost(request);
        return ResponseEntity
                .status(PostSuccessCode.POST_CREATED.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_CREATED));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPostList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "category", required = false) String category
    ){
        return ResponseEntity
                .status(PostSuccessCode.POST_READ.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_READ, postService.getPostList(page, size, category)));
    }

    @GetMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable(name = "postId") Long id
    ){
        return ResponseEntity
                .status(PostSuccessCode.POST_READ.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_READ, postService.getPost(id)));

    }

    @PutMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable(name="postId") Long id,
            @Valid @RequestBody PostUpdateRequest request
            ){
        postService.updatePost(id, request);
        return ResponseEntity
                .status(PostSuccessCode.POST_UPDATED.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_UPDATED));
    }

    @DeleteMapping(path="/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable(name="postId") Long id
    ){
        postService.deletePost(id);
        return ResponseEntity
                .status(PostSuccessCode.POST_DELETED.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_DELETED));
    }
}
