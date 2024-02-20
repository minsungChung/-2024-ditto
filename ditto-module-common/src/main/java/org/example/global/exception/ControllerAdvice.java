package org.example.global.exception;

import org.example.global.response.BaseResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public BaseResponse<String> handleInvalidData(RuntimeException ex){
        return new BaseResponse(ex.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<String> handleInvalidRequestBody(){
        return new BaseResponse("잘못된 형식의 Request Body입니다.");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> handleInvalidDtoField(MethodArgumentNotValidException ex){
        FieldError firstFieldError = ex.getFieldErrors().get(0);
        return new BaseResponse(firstFieldError.getDefaultMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<String> handleTypeMismatch(){
        return new BaseResponse("잘못된 데이터 타입입니다.");
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<String> handleNotSupportedMethod(){
        return new BaseResponse("지원하지 않는 HTTP 메소드 요청입니다.");
    }
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleUnexpectedException(Exception ex){
        return new BaseResponse("예상하지 못한 서버 에러가 발생했습니다.");
    }
}
