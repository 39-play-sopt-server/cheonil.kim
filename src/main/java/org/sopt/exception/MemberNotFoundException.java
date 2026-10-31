package org.sopt.exception;

import org.sopt.code.MemberErrorCode;

public class MemberNotFoundException extends MemberException{
    public MemberNotFoundException(){
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
