package com.finnai.project.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리기
 * 애플리케이션 전체에서 발생하는 예외를 자동으로 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // entity notfound
    // illger
    // 잘못된 파라미터, 유효성 검증 실패
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

//    // NullPointerException
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<ApiResponse<?>> handleNullPointer(NullPointerException e) {
//        ApiResponse<?> response = ApiResponse.error(
//            ResponseCode.F_SERVER_ERROR,
//            "데이터 처리 중 오류가 발생했습니다"
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
//
//    // RuntimeException (위에서 처리 안된 모든 런타임 예외)
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
//        ApiResponse<?> response = ApiResponse.error(
//            ResponseCode.F_SERVER_ERROR,
//            e.getMessage()
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
//
//    // 모든 예외의 최상위 처리 (마지막 안전망)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
//        e.printStackTrace();
//        ApiResponse<?> response = ApiResponse.error(
//            ResponseCode.F_SERVER_ERROR,
//            "서버 오류가 발생했습니다"
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
}
