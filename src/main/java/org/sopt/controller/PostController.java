package org.sopt.controller;

import org.sopt.code.PostErrorCode;
import org.sopt.code.PostSuccessCode;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostException;
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
    //request dto 만들어서 매핑 어노테이션 추가 필요
    public ResponseEntity<ApiResponse<Void>> createPost(String title, PostCategory category, String content){
        postService.createPost(title, category, content);
        return ResponseEntity
                .status(PostSuccessCode.POST_CREATED.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_CREATED));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostList(
            @RequestParam(name = "page", defaultValue = "1") Long page
    ){
        return ResponseEntity
                .status(PostSuccessCode.POST_READ.getHttpStatus())
                .body(ApiResponse.success(PostSuccessCode.POST_READ, postService.getPostList()));
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
    public ResponseEntity<ApiResponse<Void>> updatePost(Long id, String newTitle, String newContent
    ){
        postService.updatePost(id, newTitle, newContent);
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
