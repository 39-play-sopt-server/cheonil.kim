package org.sopt.exception;

import org.sopt.code.AuthErrorCode;

public class AuthException extends BusinessException{
    public AuthException(AuthErrorCode errorCode){
        super(errorCode);
    }
}
