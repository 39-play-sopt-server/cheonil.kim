package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
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
                switch (command) {
                    case 1:
                        // 게시글 작성
                        createPost();
                        break;

                    case 2:
                        // 게시글 목록 조회
                        getPostList();
                        break;

                    case 3:
                        // 게시글 단건 조회
                        getPost();
                        break;

                    case 4:
                        // 게시글 수정
                        updatePost();
                        break;

                    case 5:
                        // 게시글 삭제
                        deletePost();
                        break;

                    case 6:
                        outputView.printMessage("프로그램을 종료합니다.");
                        return;

                    default:
                        outputView.printMessage("잘못된 입력입니다.");
                }
            }
            catch (PostException e){
                outputView.printMessage(e.getMessage());
            }
        }
    }

    private void createPost(){
        String title = inputView.scanTitle();
        PostCategory category = inputView.scanCategory();
        String content = inputView.scanContent();

        postService.createPost(title, category, content);

        outputView.printMessage("게시글이 작성되었습니다.");
    }

    private void getPostList(){
        outputView.printPostList(postService.getPostList());
    }

    private void getPost(){
        int targetId = inputView.scanId();

        Post targetPost = postService.getPost(targetId);
        outputView.printPostDetail(targetPost);
    }

    private void updatePost(){
        int updateId = inputView.scanUpdateId();
        postService.getPost(updateId); //게시글이 존재하는지 확인용

        String newTitle=inputView.scanNewTitle();
        String newContent=inputView.scanNewContent();
        postService.updatePost(updateId, newTitle, newContent);
        outputView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost(){
        int deleteId=inputView.scanDeleteId();
        postService.deletePost(deleteId);
        outputView.printMessage("게시글이 삭제되었습니다.");
    }
}
