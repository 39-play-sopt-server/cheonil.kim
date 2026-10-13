package org.sopt.controller;

import org.sopt.code.PostSuccessCode;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostException;
import org.sopt.service.PostService;
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
    public ApiResponse<Void> createPost(String title, PostCategory category, String content){
        try {
            postService.createPost(title, category, content);
            return ApiResponse.success(PostSuccessCode.POST_CREATED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPostList(
            @RequestParam(name = "page", defaultValue = "1") Long page
    ){
        try {
            return ApiResponse.success(PostSuccessCode.POST_READ, postService.getPostList());
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    @GetMapping(path="/{postId}")
    public ApiResponse<PostResponse> getPost(
            @PathVariable(name = "postId") Long id){
        try {
            return ApiResponse.success(PostSuccessCode.POST_READ, postService.getPost(id));
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    @PutMapping(path="/{postId}")
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent){
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success(PostSuccessCode.POST_UPDATED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    @DeleteMapping(path="/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable(name="postId") Long id){
        try {
            postService.deletePost(id);
            return ApiResponse.success(PostSuccessCode.POST_DELETED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }
}
