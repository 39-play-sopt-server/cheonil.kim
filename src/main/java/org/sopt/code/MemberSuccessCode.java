package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessCode{
    MEMBER_READ(HttpStatus.OK, 200, "회원 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    MemberSuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
