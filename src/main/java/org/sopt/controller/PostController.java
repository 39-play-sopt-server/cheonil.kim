package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.exception.PostException;
import org.sopt.service.PostService;
import org.sopt.view.PostView;

import java.util.List;

public class PostController {
    PostService postService;
    PostView postView;

    public PostController(PostService service, PostView postView) {
        this.postService = service;
        this.postView = postView;
    }

    public void run() {
        while (true) {
            try {
                int command = postView.mainView();
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
                        postView.printMessage("프로그램을 종료합니다.");
                        return;

                    default:
                        postView.printMessage("잘못된 입력입니다.");
                }
            }
            catch (PostException e){
                postView.printMessage(e.getMessage());
            }
        }
    }

    private void createPost(){
        String title = postView.scanTitle();
        PostCategory category = postView.scanCategory();
        String content = postView.scanContent();

        postService.createPost(title, category, content);

        postView.printMessage("게시글이 작성되었습니다.");
    }

    private void getPostList(){
        postView.printMessage("\n=== 게시글 목록 ===");

        List<Post> posts = postService.getPostList();
        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        postView.printPostList(posts);
    }

    private void getPost(){
        int targetId = postView.scanId();

        Post targetPost = postService.getPost(targetId);

        postView.printMessage("\n=== 게시글 ===");
        postView.printMessage("제목: " + targetPost.getTitle());
        postView.printMessage("카테고리: " + targetPost.getCategory());
        postView.printMessage("작성일: " + targetPost.getWrittenDate());
        postView.printMessage("내용: " + targetPost.getContent());
    }

    private void updatePost(){
        int updateId = postView.scanUpdateId();
        postService.getPost(updateId); //게시글이 존재하는지 확인용

        String newTitle=postView.scanNewTitle();
        String newContent=postView.scanNewContent();
        postService.updatePost(updateId, newTitle, newContent);
        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost(){
        int deleteId=postView.scanDeleteId();
        postService.deletePost(deleteId);
        postView.printMessage("게시글이 삭제되었습니다.");
    }
}
