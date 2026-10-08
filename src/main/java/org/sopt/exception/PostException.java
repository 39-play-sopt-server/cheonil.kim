package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class PostException extends RuntimeException{
    private final ErrorCode errorCode;

    public PostException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
