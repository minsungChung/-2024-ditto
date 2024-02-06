package org.example.global.exception;

public class InvalidEmailException extends CustomException {
    public InvalidEmailException(){
        super("인증되지 않은 이메일입니다.");
    }

    public InvalidEmailException(String message){
        super(message);
    }
}
