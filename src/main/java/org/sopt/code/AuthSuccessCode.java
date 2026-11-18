package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum AuthSuccessCode implements SuccessCode{
    SIGNUP_SUCCESS(HttpStatus.CREATED, 201, "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, 200, "로그인에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, 200, "토큰이 재발급되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, 200, "로그아웃되었습니다."),
    WITHDRAW_SUCCESS(HttpStatus.OK, 200, "회원 탈퇴가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    AuthSuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
