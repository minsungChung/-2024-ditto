package hanghae99.ditto.member.exception;

import hanghae99.ditto.global.exception.CustomException;

public class NoSuchMemberException extends CustomException {

    public NoSuchMemberException(){
        super("존재하지 않는 회원입니다.");
    }

    public NoSuchMemberException(String message){
        super(message);
    }
}
