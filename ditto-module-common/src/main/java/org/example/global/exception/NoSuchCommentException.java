package org.example.global.exception;

public class NoSuchCommentException extends CustomException {
    public NoSuchCommentException(){
        super("존재하지 않는 댓글입니다.");
    }

    public NoSuchCommentException(String message){
        super(message);
    }
}
