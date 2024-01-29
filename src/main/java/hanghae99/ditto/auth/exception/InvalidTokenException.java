package hanghae99.ditto.auth.exception;

import hanghae99.ditto.global.exception.CustomException;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException(){
        super("유효하지 않은 토큰입니다.");
    }

    public InvalidTokenException(String message){
        super(message);
    }
}
