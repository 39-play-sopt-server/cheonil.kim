package org.sopt.code;

public enum PostSuccessCode implements SuccessCode{
    POST_CREATED(200, "게시글이 작성되었습니다."),
    POST_READ(200, "조회에 성공했습니다."),
    POST_UPDATED(200, "게시글이 수정되었습니다."),
    POST_DELETED(200, "게시글이 삭제되었습니다.");

    private final int code;
    private final String message;

    PostSuccessCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
}
