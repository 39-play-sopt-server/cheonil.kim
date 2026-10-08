package org.sopt.exception;

import org.sopt.code.ErrorCode;

public class InvalidPostException extends PostException{
    public InvalidPostException(ErrorCode errorCode){
        super(errorCode);
    }
}
