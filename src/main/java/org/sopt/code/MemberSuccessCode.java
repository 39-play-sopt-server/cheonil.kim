package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessCode{
    MEMBER_CREATED(HttpStatus.CREATED, 201, "회원이 생성되었습니다."),
    MEMBER_DELETED(HttpStatus.OK, 200, "회원이 삭제되었습니다.");

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
