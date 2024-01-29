package hanghae99.ditto.auth.exception;

import hanghae99.ditto.global.exception.CustomException;

public class InvalidAccessException extends CustomException {
    public InvalidAccessException(){
        super("존재하지 않는 댓글입니다.");
    }

    public InvalidAccessException(String message){
        super(message);
    }
}
