package org.sopt.view;

import org.sopt.domain.Post;
import java.util.List;

public class OutputView {

    public void printMenu(){
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public void printPostList(List<Post> posts){
        System.out.println("\n=== 게시글 목록 ===");
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (Post post : posts) {
            System.out.println(post.getId() + ". " + post.getTitle());
        }
    }

    public void printPostDetail(Post post){
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("카테고리: " + post.getCategory());
        System.out.println("작성일: " + post.getWrittenDate());
        System.out.println("내용: " + post.getContent());
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
