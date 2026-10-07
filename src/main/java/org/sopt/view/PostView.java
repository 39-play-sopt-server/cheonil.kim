package org.sopt.view;

import org.sopt.domain.PostCategory;
import org.sopt.exception.InvalidPostException;

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

        return scanNumber();
    }

    private String scan(){
        String input=scanner.nextLine();
        if (input.isBlank()) {
            throw new InvalidPostException("공백은 입력할 수 없습니다.");
        } else {
            return input;
        }
    }

    private int scanNumber(){
        String input = scan();
        try{
            return Integer.parseInt(input);
        } catch(NumberFormatException e){
            throw new InvalidPostException("숫자를 입력해주세요.");
        }
    }

    public void printPostList(List<String> titles){
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
    }

    public String scanTitle(){
        System.out.print("제목: ");
        return scan();
    }

    public PostCategory scanCategory(){
        System.out.print("카테고리(1.질문, 2.후기, 3.일기, 4.기타 중 숫자로 입력): ");
        String input=scan();
        return switch (input) {
            case "1" -> PostCategory.QUESTION;
            case "2" -> PostCategory.REVIEW;
            case "3" -> PostCategory.DIARY;
            case "4" -> PostCategory.ETC;
            default -> throw new InvalidPostException("1,2,3,4 중 하나를 입력하세요.");
        };
    }

    public String scanContent(){
        System.out.print("내용: ");
        return scan();
    }

    public int scanIndex(){
        System.out.print("조회할 게시글 번호: ");
        return scanNumber() - 1;
    }

    public int scanUpdateIndex(){
        System.out.print("수정할 게시글 번호: ");
        return scanNumber() - 1;
    }

    public String scanNewTitle(){
        System.out.print("새로운 제목: ");
        return scan();
    }

    public String scanNewContent(){
        System.out.print("새로운 내용: ");
        return scan();
    }

    public int scanDeleteIndex(){
        System.out.print("삭제할 게시글 번호: ");
        return scanNumber() - 1;
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
