package org.sopt.client;

import org.sopt.code.PostErrorCode;
import org.sopt.controller.PostController;
import org.sopt.domain.PostCategory;
import org.sopt.dto.ApiResponse;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostException;
import org.sopt.view.InputView;
import org.sopt.view.OutputView;

public class PostClient {
    private final PostController postController;
    private final InputView inputView;
    private final OutputView outputView;

    public PostClient(PostController postController, InputView inputView, OutputView outputView) {
        this.postController = postController;
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
                    case 2 -> postController.getPostList();
                    case 3 -> postController.getPost(inputView.scanId());
                    case 4 -> updatePost();
                    case 5 -> postController.deletePost(inputView.scanDeleteId());
                    default -> ApiResponse.fail(PostErrorCode.INVALID_COMMAND);
                };

                outputView.printResponse(response);
            }
            catch (PostException e){ //입력 형식 오류는 서버에 보내기 전에 클라이언트가 처리
                outputView.printResponse(ApiResponse.fail(e.getErrorCode()));
            }
        }
    }

    private ApiResponse<Void> createPost(){
        String title = inputView.scanTitle();
        PostCategory category = inputView.scanCategory();
        String content = inputView.scanContent();

        return postController.createPost(title, category, content);
    }

    private ApiResponse<?> updatePost(){
        int updateId = inputView.scanUpdateId();

        ApiResponse<PostResponse> found = postController.getPost(updateId); //게시글이 존재하는지 확인
        if (found.data() == null) {
            return found;
        }

        String newTitle = inputView.scanNewTitle();
        String newContent = inputView.scanNewContent();

        return postController.updatePost(updateId, newTitle, newContent);
    }
}
