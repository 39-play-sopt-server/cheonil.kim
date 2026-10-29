package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode{
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 게시글입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, 409, "이미 사용 중인 이메일입니다."),
    BLANK_INPUT(HttpStatus.BAD_REQUEST, 400, "공백은 입력할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, int code, String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
