package com.finnai.project.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 전역 예외 처리기
 * 애플리케이션 전체에서 발생하는 예외를 자동으로 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 잘못된 파라미터, 유효성 검증 실패
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", e.getMessage()));
    }

    // 데이터 못 찾음
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointer(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", "데이터를 찾을 수 없습니다"));
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "서버 오류가 발생했습니다"));
    }
}
