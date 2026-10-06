package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;
import org.sopt.view.PostView;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    PostService postService = new PostService();
    PostView postView = new PostView();

    public void run() {
        while (true) {
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
    }

    private void createPost(){
        String title = postView.scanTitle();
        String content = postView.scanContent();

        postService.createPost(title, content);

        postView.printMessage("게시글이 작성되었습니다.");
    }

    private void getPostList(){
        postView.printMessage("\n=== 게시글 목록 ===");

        List<String> posts = postService.getPostTitleList();
        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        postView.printPostList(posts);
    }

    private void getPost(){
        if (postService.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int targetIndex = postView.scanIndex();

        Post targetPost = postService.getPost(targetIndex);

        if (targetPost==null) {
            postView.printMessage("존재하지 않는 게시글입니다.");
        } else{
            postView.printMessage("\n=== 게시글 ===");
            postView.printMessage("제목: " + targetPost.getTitle());
            postView.printMessage("내용: " + targetPost.getContent());
        }


    }

    private void updatePost(){
        if (postService.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int updateIndex = postView.scanUpdateIndex();

        if (!postService.isPostExist(updateIndex)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }


        String newTitle=postView.scanNewTitle();
        String newContent=postView.scanNewContent();
        postService.updatePost(updateIndex, newTitle, newContent);
        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost(){
        if (postService.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int deleteIndex=postView.scanDeleteIndex();

        if (!postService.isPostExist(deleteIndex)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        postService.deletePost(deleteIndex);

        postView.printMessage("게시글이 삭제되었습니다.");
    }
}
