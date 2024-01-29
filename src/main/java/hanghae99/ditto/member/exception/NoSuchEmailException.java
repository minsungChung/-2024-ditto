package hanghae99.ditto.member.exception;

import hanghae99.ditto.global.exception.CustomException;

public class NoSuchEmailException extends CustomException {
    public NoSuchEmailException(){
        super("존재하지 않는 이메일입니다.");
    }

    public NoSuchEmailException(String message){
        super(message);
    }
}
