package org.sopt.exception;

import org.sopt.code.ErrorCode;
import org.sopt.code.GlobalErrorCode;
import org.sopt.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<BaseResponse<Void>> handlerPostException(PostException e){
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.fail(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handlerException(Exception e){
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(BaseResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,      // @Valid 실패
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class       // 깨진 JSON 본문
    })
    public ResponseEntity<BaseResponse<Void>> handleInvalidInput(Exception e){
        return ResponseEntity
                .status(GlobalErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(BaseResponse.fail(GlobalErrorCode.INVALID_INPUT_VALUE));
    }
}
