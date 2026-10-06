package org.sopt;

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

        int command = Integer.parseInt(scanner.nextLine());

        return command;
    }

    public String scanTitle(){
        System.out.print("제목: ");
        String title = scanner.nextLine();
        return title;
    }

    public String scanContent(){
        System.out.print("내용: ");
        String content = scanner.nextLine();
        return content;
    }

    public int scanIndex(){
        System.out.print("조회할 게시글 번호: ");
        int readIndex = Integer.parseInt(scanner.nextLine()) - 1;
        return readIndex;
    }

    public int scanUpdateIndex(){
        System.out.print("수정할 게시글 번호: ");
        int updateIndex = Integer.parseInt(scanner.nextLine()) - 1;
        return updateIndex;
    }

    public String scanNewTitle(){
        System.out.print("새로운 제목: ");
        String newTitle = scanner.nextLine();
        return newTitle;
    }

    public String scanNewContent(){
        System.out.print("새로운 내용: ");
        String newContent = scanner.nextLine();
        return newContent;
    }

    public int scanDeleteIndex(){
        System.out.print("삭제할 게시글 번호: ");
        int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
        return deleteIndex;
    }
}
