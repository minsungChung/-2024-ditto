package hanghae99.ditto.global.exception;

public class CustomException extends RuntimeException{

    private CustomException() {}

    protected CustomException(String message) {super(message);}
}
