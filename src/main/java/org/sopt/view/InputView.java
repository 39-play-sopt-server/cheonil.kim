package org.sopt.view;

import org.sopt.code.PostErrorCode;
import org.sopt.domain.PostCategory;
import org.sopt.exception.InvalidPostException;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    private String scan(){
        String input=scanner.nextLine();
        if (input.isBlank()) {
            throw new InvalidPostException(PostErrorCode.BLANK_INPUT);
        } else {
            return input;
        }
    }

    private int scanNumber(){
        String input = scan();
        try{
            return Integer.parseInt(input);
        } catch(NumberFormatException e){
            throw new InvalidPostException(PostErrorCode.NOT_A_NUMBER);
        }
    }

    public int scanCommand(){
        System.out.print("선택: ");
        return scanNumber();
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
            default -> throw new InvalidPostException(PostErrorCode.INVALID_CATEGORY);
        };
    }

    public String scanContent(){
        System.out.print("내용: ");
        return scan();
    }

    public int scanId(){
        System.out.print("조회할 게시글 번호: ");
        return scanNumber();
    }

    public int scanUpdateId(){
        System.out.print("수정할 게시글 번호: ");
        return scanNumber();
    }

    public String scanNewTitle(){
        System.out.print("새로운 제목: ");
        return scan();
    }

    public String scanNewContent(){
        System.out.print("새로운 내용: ");
        return scan();
    }

    public int scanDeleteId(){
        System.out.print("삭제할 게시글 번호: ");
        return scanNumber();
    }
}
