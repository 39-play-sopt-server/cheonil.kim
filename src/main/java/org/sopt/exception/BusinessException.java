package org.sopt.exception;

import org.sopt.code.ErrorCode;

public abstract class BusinessException extends RuntimeException {
    public final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
