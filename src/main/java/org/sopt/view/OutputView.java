package org.sopt.view;

import org.sopt.dto.PostResponse;

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

    public void printPostList(List<PostResponse> posts){
        System.out.println("\n=== 게시글 목록 ===");
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (PostResponse post : posts) {
            System.out.println(post.id() + ". " + post.title());
        }
    }

    public void printPostDetail(PostResponse post){
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.title());
        System.out.println("카테고리: " + post.category());
        System.out.println("작성일: " + post.writtenDate());
        System.out.println("내용: " + post.content());
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
