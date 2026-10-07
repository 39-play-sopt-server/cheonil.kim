package org.sopt.exception;

public class PostNotFoundException extends PostException{
    public PostNotFoundException() {super("존재하지 않는 게시글입니다.");}
}
