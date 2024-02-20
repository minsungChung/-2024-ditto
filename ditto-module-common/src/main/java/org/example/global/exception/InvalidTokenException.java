package org.example.global.exception;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException(){
        super("유효하지 않은 토큰입니다.");
    }

    public InvalidTokenException(String message){
        super(message);
    }
}
