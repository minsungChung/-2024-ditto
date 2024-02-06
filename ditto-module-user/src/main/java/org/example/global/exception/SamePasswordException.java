package org.example.global.exception;

public class SamePasswordException extends CustomException {
    public SamePasswordException() {
        super("기존 비밀번호와 동일합니다.");
    }
}
