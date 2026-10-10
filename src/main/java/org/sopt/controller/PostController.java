package org.sopt.controller;

import org.sopt.code.PostSuccessCode;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ApiResponse<Void> createPost(String title, PostCategory category, String content){
        try {
            postService.createPost(title, category, content);
            return ApiResponse.success(PostSuccessCode.POST_CREATED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    public ApiResponse<List<PostResponse>> getPostList(){
        try {
            return ApiResponse.success(PostSuccessCode.POST_READ, postService.getPostList());
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    public ApiResponse<PostResponse> getPost(int id){
        try {
            return ApiResponse.success(PostSuccessCode.POST_READ, postService.getPost(id));
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    public ApiResponse<Void> updatePost(int id, String newTitle, String newContent){
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success(PostSuccessCode.POST_UPDATED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }

    public ApiResponse<Void> deletePost(int id){
        try {
            postService.deletePost(id);
            return ApiResponse.success(PostSuccessCode.POST_DELETED);
        } catch (PostException e) {
            return ApiResponse.fail(e.getErrorCode());
        }
    }
}
