package hanghae99.ditto.auth.exception;

import hanghae99.ditto.global.exception.CustomException;

public class InvalidAccessException extends CustomException {
    public InvalidAccessException(){
        super("권한이 없는 사용자입니다.");
    }

    public InvalidAccessException(String message){
        super(message);
    }
}
