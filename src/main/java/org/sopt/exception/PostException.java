package org.sopt.exception;

import org.sopt.code.ErrorCode;

public abstract class PostException extends BusinessException{
    protected PostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
