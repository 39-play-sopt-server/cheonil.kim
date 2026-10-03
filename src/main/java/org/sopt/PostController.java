package org.sopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostController {
    List<Post> posts = new ArrayList<>();
    PostView postView = new PostView();

    public void run() {
        while (true) {
            int command = postView.mainView();

            switch (command) {
                case 1:
                    // 게시글 작성
                    String title = postView.scanTitle();
                    String content = postView.scanContent();

                    Post post = new Post(title, content);
                    posts.add(post);

                    System.out.println("게시글이 작성되었습니다.");
                    break;

                case 2:
                    // 게시글 목록 조회
                    System.out.println("\n=== 게시글 목록 ===");

                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    for (int i = 0; i < posts.size(); i++) {
                        Post currentPost = posts.get(i);

                        System.out.println(
                                (i + 1) + ". " + currentPost.getTitle()
                        );
                    }
                    break;

                case 3:
                    // 게시글 단건 조회
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    int readIndex = postView.scanIndex();

                    if (readIndex < 0 || readIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    Post readPost = posts.get(readIndex);

                    System.out.println("\n=== 게시글 ===");
                    System.out.println("제목: " + readPost.getTitle());
                    System.out.println("내용: " + readPost.getContent());
                    break;

                case 4:
                    // 게시글 수정
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }
                    int updateIndex = postView.scanUpdateIndex();

                    if (updateIndex < 0 || updateIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    Post updatePost = posts.get(updateIndex);

                    String newTitle=postView.scanNewTitle();
                    String newContent=postView.scanNewContent();

                    updatePost.updateTitle(newTitle);
                    updatePost.updateContent(newContent);

                    System.out.println("게시글이 수정되었습니다.");
                    break;

                case 5:
                    // 게시글 삭제
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }
                    int deleteIndex=postView.scanDeleteIndex();

                    if (deleteIndex < 0 || deleteIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    posts.remove(deleteIndex);

                    System.out.println("게시글이 삭제되었습니다.");
                    break;

                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
