package com.finnai.project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ========================================
 * ApiResponseSample - 모든 API 응답의 공통 포맷
 * ========================================
 *
 * [역할]
 * - 모든 Controller에서 리턴하는 응답을 통일된 형식으로 만들어줍니다
 * - 성공/실패 모두 같은 구조로 응답합니다
 *
 * [응답 구조]
 * {
 *   "code": "T-COMPANY-001",      // 응답 코드 (성공:T, 실패:F)
 *   "message": "조회 성공",        // 응답 메시지
 *   "data": { ... }               // 실제 데이터 (실패시 null)
 * }
 *
 * [사용 위치]
 * - Controller에서 리턴할 때 사용
 *
 * [전체 흐름]
 * 1. Client가 API 요청
 * 2. Controller에서 처리
 * 3. ApiResponseSample.success() 또는 error() 호출
 * 4. Client에게 통일된 형식으로 응답
 *
 * [예시]
 * // 성공
 * return ApiResponseSample.success(ResponseCodeSample.COMPANY_DETAIL_SUCCESS, companyData);
 *
 * // 실패 (자동으로 GlobalExceptionHandlerSample이 처리)
 * throw new RuntimeException("에러 발생");
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON에 포함 안함
public class ApiResponseSample<T> {

    private String code;        // 응답 코드 (예: T-COMPANY-001, F-USER-404)
    private String message;     // 응답 메시지 (예: "조회 성공", "존재하지 않는 사용자")
    private T data;            // 실제 데이터 (제네릭 타입 - 어떤 타입이든 가능)

    /**
     * 성공 응답 생성 (데이터 있음)
     *
     * @param responseCode 응답 코드 (ResponseCodeSample에서 선택)
     * @param data 클라이언트에게 전달할 실제 데이터
     * @return ApiResponseSample 객체
     *
     * 사용 예시:
     * CompanyDto company = service.getCompany(1);
     * return ApiResponseSample.success(ResponseCodeSample.COMPANY_DETAIL_SUCCESS, company);
     */
    public static <T> ApiResponseSample<T> success(ResponseCodeSample responseCode, T data) {
        return new ApiResponseSample<>(
            responseCode.getCode(),      // "T-COMPANY-001"
            responseCode.getMessage(),   // "기업 상세 조회 성공"
            data                        // 실제 데이터
        );
    }

    /**
     * 성공 응답 생성 (데이터 없음)
     *
     * @param responseCode 응답 코드
     * @return ApiResponseSample 객체
     *
     * 사용 예시:
     * service.deleteCompany(1);
     * return ApiResponseSample.success(ResponseCodeSample.COMPANY_DELETE_SUCCESS);
     */
    public static <T> ApiResponseSample<T> success(ResponseCodeSample responseCode) {
        return new ApiResponseSample<>(
            responseCode.getCode(),
            responseCode.getMessage(),
            null  // 데이터 없음
        );
    }

    /**
     * 실패 응답 생성
     *
     * @param responseCode 에러 코드
     * @return ApiResponseSample 객체
     *
     * 사용 예시:
     * // 보통은 직접 호출 안하고, GlobalExceptionHandlerSample이 자동으로 호출함
     * return ApiResponseSample.error(ResponseCodeSample.COMPANY_NOT_FOUND);
     */
    public static <T> ApiResponseSample<T> error(ResponseCodeSample responseCode) {
        return new ApiResponseSample<>(
            responseCode.getCode(),
            responseCode.getMessage(),
            null  // 실패시 데이터 없음
        );
    }

    /**
     * 실패 응답 생성 (커스텀 메시지)
     *
     * @param responseCode 에러 코드
     * @param customMessage 커스텀 에러 메시지
     * @return ApiResponseSample 객체
     */
    public static <T> ApiResponseSample<T> error(ResponseCodeSample responseCode, String customMessage) {
        return new ApiResponseSample<>(
            responseCode.getCode(),
            customMessage,  // 기본 메시지 대신 커스텀 메시지 사용
            null
        );
    }
}

/*
 * ========================================
 * 실제 JSON 응답 예시
 * ========================================
 *
 * [성공 시]
 * {
 *   "code": "T-COMPANY-001",
 *   "message": "기업 상세 조회 성공",
 *   "data": {
 *     "companyId": 1,
 *     "name": "삼성전자",
 *     "industry": "전자제품"
 *   }
 * }
 *
 * [실패 시]
 * {
 *   "code": "F-COMPANY-404",
 *   "message": "존재하지 않는 기업입니다",
 *   "data": null
 * }
 *
 * ========================================
 * 클래스 관계도
 * ========================================
 *
 * ApiResponseSample (이 클래스)
 *    ↓ 사용
 * ResponseCodeSample (코드 모음)
 *    ↓ 참조
 * Controller에서 리턴
 *    ↓
 * Client에게 JSON 응답
 */
