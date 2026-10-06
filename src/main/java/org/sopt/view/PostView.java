package org.sopt.view;

import java.util.List;
import java.util.Scanner;

public class PostView {
    Scanner scanner = new Scanner(System.in);

    public int mainView(){
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
        System.out.print("선택: ");

        return Integer.parseInt(scanner.nextLine());
    }

    public void printPostList(List<String> titles){
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
    }

    public String scanTitle(){
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String scanContent(){
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public int scanIndex(){
        System.out.print("조회할 게시글 번호: ");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public int scanUpdateIndex(){
        System.out.print("수정할 게시글 번호: ");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public String scanNewTitle(){
        System.out.print("새로운 제목: ");
        return scanner.nextLine();
    }

    public String scanNewContent(){
        System.out.print("새로운 내용: ");
        return scanner.nextLine();
    }

    public int scanDeleteIndex(){
        System.out.print("삭제할 게시글 번호: ");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
