package com.finnai.project.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ========================================
 * GlobalExceptionHandlerSample - 전역 예외 처리기
 * ========================================
 *
 * [역할]
 * - 애플리케이션 전체에서 발생하는 예외를 한 곳에서 처리
 * - 예외를 잡아서 자동으로 ApiResponseSample로 변환
 * - Controller에서 try-catch 안 써도 됨!
 *
 * [동작 원리]
 * 1. Controller나 Service에서 예외 발생 (throw new RuntimeException())
 * 2. Spring이 자동으로 이 클래스의 메서드를 찾음
 * 3. 해당하는 @ExceptionHandler 메서드 실행
 * 4. ApiResponseSample로 변환해서 Client에게 응답
 *
 * [사용 위치]
 * - 애플리케이션 전체에 자동 적용됨 (따로 호출 안해도 됨)
 *
 * [@RestControllerAdvice 어노테이션]
 * - 모든 Controller에 적용되는 전역 예외 처리기
 * - @ControllerAdvice + @ResponseBody 합친 것
 *
 * [@ExceptionHandler 어노테이션]
 * - 특정 예외가 발생했을 때 실행할 메서드 지정
 * - 예: @ExceptionHandler(NullPointerException.class)
 */
@RestControllerAdvice  // 모든 Controller의 예외를 처리
public class GlobalExceptionHandlerSample {

    /**
     * IllegalArgumentException 처리
     * - 잘못된 파라미터, 유효성 검증 실패 등
     *
     * 발생 예시:
     * if (id < 0) {
     *     throw new IllegalArgumentException("ID는 0보다 커야 합니다");
     * }
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseSample<?>> handleIllegalArgument(IllegalArgumentException e) {
        // 예외 메시지를 커스텀 메시지로 사용
        ApiResponseSample<?> response = ApiResponseSample.error(
            ResponseCodeSample.F_INVALID_INPUT,
            e.getMessage()  // 예외의 메시지 사용
        );

        // HTTP 400 Bad Request와 함께 응답
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)  // 400
            .body(response);
    }

    /**
     * NullPointerException 처리
     * - null 값에 접근할 때 발생
     *
     * 발생 예시:
     * Company company = null;
     * company.getName();  // NullPointerException 발생!
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseSample<?>> handleNullPointer(NullPointerException e) {
        ApiResponseSample<?> response = ApiResponseSample.error(
            ResponseCodeSample.F_SERVER_ERROR,
            "데이터 처리 중 오류가 발생했습니다"
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
            .body(response);
    }

    /**
     * RuntimeException 처리 (모든 런타임 예외의 부모)
     * - 위에서 처리 안된 모든 RuntimeException 처리
     *
     * 발생 예시:
     * throw new RuntimeException("예상치 못한 오류");
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseSample<?>> handleRuntimeException(RuntimeException e) {
        ApiResponseSample<?> response = ApiResponseSample.error(
            ResponseCodeSample.F_SERVER_ERROR,
            e.getMessage()
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
            .body(response);
    }

    /**
     * Exception 처리 (모든 예외의 최상위 부모)
     * - 위에서 잡히지 않은 모든 예외를 여기서 처리
     * - 마지막 안전망
     *
     * 발생 예시:
     * throw new Exception("알 수 없는 오류");
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseSample<?>> handleException(Exception e) {
        // 실제 운영에서는 로그를 남겨야 함
        e.printStackTrace();  // 콘솔에 에러 출력

        ApiResponseSample<?> response = ApiResponseSample.error(
            ResponseCodeSample.F_SERVER_ERROR,
            "서버 오류가 발생했습니다. 관리자에게 문의하세요."
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
            .body(response);
    }
}

/*
 * ========================================
 * 실제 동작 흐름 예시
 * ========================================
 *
 * [시나리오: 존재하지 않는 기업 조회]
 *
 * 1. Client 요청
 *    GET /api/v1/company/99999/summary
 *
 * 2. Controller
 *    @GetMapping("/{id}/summary")
 *    public ApiResponseSample<?> getSummary(@PathVariable Long id) {
 *        return service.getSummary(id);  // Service 호출
 *    }
 *
 * 3. Service
 *    public CompanyDto getSummary(Long id) {
 *        Company company = repository.findById(id);
 *        if (company == null) {
 *            throw new IllegalArgumentException("존재하지 않는 기업입니다");  // 예외 발생!
 *        }
 *        return company.toDto();
 *    }
 *
 * 4. GlobalExceptionHandlerSample (자동 실행!)
 *    handleIllegalArgument() 메서드가 자동으로 실행됨
 *
 * 5. Client 응답
 *    HTTP 400 Bad Request
 *    {
 *      "code": "F-COMMON-400",
 *      "message": "존재하지 않는 기업입니다",
 *      "data": null
 *    }
 *
 * ========================================
 * 예외 처리 우선순위
 * ========================================
 *
 * 1. IllegalArgumentException → handleIllegalArgument()
 * 2. NullPointerException → handleNullPointer()
 * 3. RuntimeException → handleRuntimeException()
 * 4. Exception → handleException()
 *
 * ※ 더 구체적인 예외부터 처리됨 (위에서 아래로)
 *
 * ========================================
 * ResponseEntity란?
 * ========================================
 *
 * - HTTP 응답을 만드는 클래스
 * - HTTP 상태 코드 + Body 함께 전송
 *
 * ResponseEntity.status(400).body(응답)
 *   ↓
 * HTTP/1.1 400 Bad Request
 * {
 *   "code": "F-COMMON-400",
 *   "message": "...",
 *   "data": null
 * }
 *
 * ========================================
 * 클래스 관계도
 * ========================================
 *
 * Controller/Service에서 예외 발생
 *    ↓
 * GlobalExceptionHandlerSample (자동 실행)
 *    ↓
 * @ExceptionHandler 메서드 실행
 *    ↓
 * ApiResponseSample.error() 호출
 *    ↓
 * ResponseEntity로 감싸서 리턴
 *    ↓
 * Client에게 JSON 응답
 */
