package hanghae99.ditto.comment.exception;

import hanghae99.ditto.global.exception.CustomException;

public class NoSuchCommentException extends CustomException {
    public NoSuchCommentException(){
        super("존재하지 않는 댓글입니다.");
    }

    public NoSuchCommentException(String message){
        super(message);
    }
}
