package hanghae99.ditto.member.exception;

import hanghae99.ditto.global.exception.CustomException;

public class SamePasswordException extends CustomException {
    public SamePasswordException() {
        super("기존 비밀번호와 동일합니다.");
    }
}
