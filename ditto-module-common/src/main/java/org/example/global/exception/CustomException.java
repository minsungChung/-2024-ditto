package org.example.global.exception;

public class CustomException extends RuntimeException{

    private CustomException() {}

    public CustomException(String message) {super(message);}
}
