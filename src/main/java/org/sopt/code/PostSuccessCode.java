package org.sopt.code;

import org.springframework.http.HttpStatus;

public enum PostSuccessCode implements SuccessCode{
    POST_CREATED(HttpStatus.CREATED, 201, "게시글이 작성되었습니다."),
    POST_READ(HttpStatus.OK, 200, "조회에 성공했습니다."),
    POST_UPDATED(HttpStatus.OK,200, "게시글이 수정되었습니다."),
    POST_DELETED(HttpStatus.OK,200, "게시글이 삭제되었습니다."),
    POST_LIKED(HttpStatus.OK, 200, "좋아요를 눌렀습니다."),
    POST_UNLIKED(HttpStatus.OK, 200, "좋아요를 취소했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    PostSuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
