package hanghae99.ditto.post.exception;

import hanghae99.ditto.global.exception.CustomException;

public class NoSuchPostException extends CustomException {
    public NoSuchPostException(){
        super("존재하지 않는 게시글입니다.");
    }

    public NoSuchPostException(String message){
        super(message);
    }
}
