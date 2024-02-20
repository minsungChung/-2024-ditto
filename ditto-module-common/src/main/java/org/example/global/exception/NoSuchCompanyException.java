package org.example.global.exception;

public class NoSuchCompanyException extends CustomException {

    public NoSuchCompanyException(){
        super("존재하지 않는 종목입니다.");
    }

    public NoSuchCompanyException(String message){
        super(message);
    }
}
