package org.sopt.exception;

import org.sopt.code.MemberErrorCode;

public class DuplicateEmailException extends MemberException{
    public DuplicateEmailException(){
        super(MemberErrorCode.DUPLICATE_EMAIL);
    }
}
