package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode{
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 게시글입니다."),
    BLANK_INPUT(HttpStatus.BAD_REQUEST, 400, "공백은 입력할 수 없습니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, 400, "QUESTION, REVIEW, DIARY, ETC 중 하나여야 합니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, 400, "page는 1 이상, size는 1~100 사이여야 합니다.");

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
