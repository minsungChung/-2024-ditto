package org.example.global.exception;

public class InvalidAccessException extends CustomException {
    public InvalidAccessException(){
        super("권한이 없는 사용자입니다.");
    }

    public InvalidAccessException(String message){
        super(message);
    }
}
