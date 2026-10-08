package org.sopt.controller;

import org.sopt.code.PostErrorCode;
import org.sopt.code.PostSuccessCode;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostException;
import org.sopt.service.PostService;
import org.sopt.view.InputView;
import org.sopt.view.OutputView;

import java.util.List;

public class PostController {
    private final PostService postService;
    private final InputView inputView;
    private final OutputView outputView;

    public PostController(PostService service, InputView inputView, OutputView outputView) {
        this.postService = service;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (true) {
            try {
                outputView.printMenu();
                int command = inputView.scanCommand();

                if (command == 6){
                    outputView.printMessage("프로그램을 종료합니다.");
                    return;
                }

                ApiResponse<?> response = switch (command){
                    case 1 -> createPost();
                    case 2 -> getPostList();
                    case 3 -> getPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    default -> ApiResponse.fail(PostErrorCode.INVALID_COMMAND);
                };

                outputView.printResponse(response);
            }
            catch (PostException e){
                outputView.printResponse(ApiResponse.fail(e.getErrorCode()));
            }
        }
    }

    private ApiResponse<Void> createPost(){
        String title = inputView.scanTitle();
        PostCategory category = inputView.scanCategory();
        String content = inputView.scanContent();

        postService.createPost(title, category, content);

        return ApiResponse.success(PostSuccessCode.POST_CREATED);
    }

    private ApiResponse<List<PostResponse>> getPostList(){
        return ApiResponse.success(PostSuccessCode.POST_READ, postService.getPostList());
    }

    private ApiResponse<PostResponse> getPost(){
        int targetId = inputView.scanId();
        PostResponse targetPost = postService.getPost(targetId);
        return ApiResponse.success(PostSuccessCode.POST_READ, targetPost);
    }

    private ApiResponse<Void> updatePost(){
        int updateId = inputView.scanUpdateId();
        postService.getPost(updateId); //게시글이 존재하는지 확인

        String newTitle=inputView.scanNewTitle();
        String newContent=inputView.scanNewContent();
        postService.updatePost(updateId, newTitle, newContent);

        return ApiResponse.success(PostSuccessCode.POST_UPDATED);
    }

    private ApiResponse<Void> deletePost(){
        int deleteId=inputView.scanDeleteId();
        postService.deletePost(deleteId);

        return ApiResponse.success(PostSuccessCode.POST_DELETED);
    }
}
