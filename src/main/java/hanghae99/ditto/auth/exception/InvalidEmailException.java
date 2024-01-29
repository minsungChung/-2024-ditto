package hanghae99.ditto.auth.exception;

import hanghae99.ditto.global.exception.CustomException;

public class InvalidEmailException extends CustomException {
    public InvalidEmailException(){
        super("인증되지 않은 이메일입니다.");
    }

    public InvalidEmailException(String message){
        super(message);
    }
}
