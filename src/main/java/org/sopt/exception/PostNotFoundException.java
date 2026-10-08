package org.sopt.exception;

import org.sopt.code.PostErrorCode;

public class PostNotFoundException extends PostException{
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
