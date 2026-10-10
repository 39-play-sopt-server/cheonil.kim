package org.sopt.code;

public enum PostErrorCode implements ErrorCode{
    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    BLANK_INPUT(400, "공백은 입력할 수 없습니다."),
    NOT_A_NUMBER(400, "숫자를 입력해주세요."),
    INVALID_CATEGORY(400, "1,2,3,4 중 하나를 입력하세요."),
    INVALID_COMMAND(400, "잘못된 입력입니다.");

    private final int code;
    private final String message;

    PostErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
