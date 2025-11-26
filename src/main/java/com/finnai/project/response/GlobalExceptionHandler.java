package com.finnai.project.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<GlobalApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("BusinessException: {}", errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(GlobalApiResponse.error(errorCode));
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<GlobalApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}

