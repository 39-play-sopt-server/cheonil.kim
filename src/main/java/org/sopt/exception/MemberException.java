package org.sopt.exception;

import org.sopt.code.ErrorCode;

public abstract class MemberException extends BusinessException {
    protected MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
