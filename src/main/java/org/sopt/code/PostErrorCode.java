package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode{
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 게시글입니다."),
    BLANK_INPUT(HttpStatus.BAD_REQUEST, 400, "공백은 입력할 수 없습니다."),
    NOT_A_NUMBER(HttpStatus.BAD_REQUEST, 400, "숫자를 입력해주세요."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, 400, "1,2,3,4 중 하나를 입력하세요."),
    INVALID_COMMAND(HttpStatus.BAD_REQUEST, 400, "잘못된 입력입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, int code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
