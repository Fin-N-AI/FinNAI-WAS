package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * ========================================
 * CompanyControllerSample - 실제 사용 예시
 * ========================================
 *
 * [역할]
 * - ApiResponseSample, ResponseCodeSample를 실제로 어떻게 사용하는지 보여주는 예시
 * - Mock 데이터로 실제 동작을 시뮬레이션
 *
 * [전체 흐름 한눈에 보기]
 *
 * 1. 성공 케이스
 *    Client 요청 → Controller → Mock 데이터 생성
 *    → ApiResponseSample.success() → Client 응답
 *
 * 2. 실패 케이스
 *    Client 요청 → Controller → 예외 발생 (throw)
 *    → GlobalExceptionHandlerSample → ApiResponseSample.error()
 *    → Client 응답
 *
 * [테스트 방법]
 * - 성공: http://localhost:8080/api/v1/sample/companies/1/summary
 * - 실패: http://localhost:8080/api/v1/sample/companies/99999/summary
 */
@RestController
@RequestMapping("/api/v1/sample/companies")
public class CompanyControllerSample {

    /**
     * ========================================
     * 예시 1: 기업 목록 조회 (성공)
     * ========================================
     *
     * [요청]
     * GET /api/v1/sample/companies/summaries
     *
     * [응답]
     * {
     *   "code": "T-COMPANY-001",
     *   "message": "기업 목록 조회 성공",
     *   "data": [
     *     { "companyId": 1, "name": "삼성전자", ... },
     *     { "companyId": 2, "name": "SK하이닉스", ... }
     *   ]
     * }
     */
    @GetMapping("/summaries")
    public ApiResponseSample<List<CompanyDto>> getCompanyList() {
        // Mock 데이터 생성
        List<CompanyDto> mockData = Arrays.asList(
            new CompanyDto(1L, "삼성전자", "전자제품", "3000억"),
            new CompanyDto(2L, "SK하이닉스", "반도체", "2000억"),
            new CompanyDto(3L, "현대자동차", "자동차", "1500억")
        );

        // ApiResponseSample.success() 사용
        return ApiResponseSample.success(
            ResponseCodeSample.T_COMPANY_LIST_SUCCESS,  // 성공 코드 선택
            mockData                                     // 데이터 전달
        );
    }

    /**
     * ========================================
     * 예시 2: 기업 상세 조회 (성공 or 실패)
     * ========================================
     *
     * [성공 요청]
     * GET /api/v1/sample/companies/1/summary
     *
     * [성공 응답]
     * {
     *   "code": "T-COMPANY-002",
     *   "message": "기업 상세 정보 조회 성공",
     *   "data": {
     *     "companyId": 1,
     *     "name": "삼성전자",
     *     "industry": "전자제품",
     *     "revenue": "3000억"
     *   }
     * }
     *
     * [실패 요청]
     * GET /api/v1/sample/companies/99999/summary
     *
     * [실패 응답]
     * {
     *   "code": "F-COMMON-400",
     *   "message": "ID 99999에 해당하는 기업이 없습니다",
     *   "data": null
     * }
     */
    @GetMapping("/{id}/summary")
    public ApiResponseSample<CompanyDto> getCompanyDetail(@PathVariable Long id) {
        // ID가 1, 2, 3이 아니면 예외 발생
        if (id < 1 || id > 3) {
            // 예외를 던지면 GlobalExceptionHandlerSample이 자동으로 처리!
            throw new IllegalArgumentException("ID " + id + "에 해당하는 기업이 없습니다");
        }

        // Mock 데이터 생성
        CompanyDto mockData = new CompanyDto(
            id,
            "삼성전자",
            "전자제품",
            "3000억"
        );

        // 성공 응답
        return ApiResponseSample.success(
            ResponseCodeSample.T_COMPANY_DETAIL_SUCCESS,
            mockData
        );
    }

    /**
     * ========================================
     * 예시 3: 관심기업 등록 (데이터 없는 성공)
     * ========================================
     *
     * [요청]
     * POST /api/v1/sample/companies/1/watch-list
     *
     * [응답]
     * {
     *   "code": "T-COMPANY-008",
     *   "message": "관심기업 등록 성공",
     *   "data": null
     * }
     */
    @PostMapping("/{id}/watch-list")
    public ApiResponseSample<Void> addToWatchList(@PathVariable Long id) {
        // 실제로는 DB에 저장하는 로직
        // ...

        // 데이터 없이 성공만 알림
        return ApiResponseSample.success(
            ResponseCodeSample.T_COMPANY_WATCHLIST_ADD_SUCCESS
        );
    }

    /**
     * ========================================
     * 예시 4: NullPointerException 발생 테스트
     * ========================================
     *
     * [요청]
     * GET /api/v1/sample/companies/error-test
     *
     * [응답]
     * {
     *   "code": "F-COMMON-500",
     *   "message": "데이터 처리 중 오류가 발생했습니다",
     *   "data": null
     * }
     */
    @GetMapping("/error-test")
    public ApiResponseSample<CompanyDto> testNullPointerError() {
        // 일부러 NullPointerException 발생시킴
        CompanyDto company = null;
        company.getName();  // NullPointerException!

        return null;  // 여기까지 오지 않음
    }

    // ========================================
    // Mock DTO 클래스
    // ========================================

    /**
     * 기업 정보 DTO (Data Transfer Object)
     * - 실제 데이터를 전달하는 객체
     * - 클라이언트에게 전송할 데이터 형식
     */
    @Getter
    @AllArgsConstructor
    static class CompanyDto {
        private Long companyId;     // 기업 ID
        private String name;        // 기업명
        private String industry;    // 산업군
        private String revenue;     // 매출액
    }
}

/*
 * ========================================
 * 전체 흐름 정리
 * ========================================
 *
 * [성공 케이스]
 * 1. Client: GET /api/v1/sample/companies/1/summary
 * 2. Controller: getCompanyDetail(1) 실행
 * 3. Mock 데이터 생성
 * 4. ApiResponseSample.success(코드, 데이터)
 * 5. Client: JSON 응답 받음
 *
 * [실패 케이스]
 * 1. Client: GET /api/v1/sample/companies/99999/summary
 * 2. Controller: getCompanyDetail(99999) 실행
 * 3. if (id > 3) → throw IllegalArgumentException
 * 4. GlobalExceptionHandlerSample: handleIllegalArgument() 자동 실행
 * 5. ApiResponseSample.error(코드, 메시지)
 * 6. Client: 에러 JSON 응답 받음
 *
 * ========================================
 * 각 파일의 역할 요약
 * ========================================
 *
 * 1. ApiResponseSample
 *    - 응답 JSON 형식 정의
 *    - success(), error() 메서드 제공
 *
 * 2. ResponseCodeSample
 *    - 모든 응답 코드 모음 (Enum)
 *    - T-XXX-001 (성공), F-XXX-404 (실패)
 *
 * 3. GlobalExceptionHandlerSample
 *    - 예외 자동 처리
 *    - try-catch 없이 예외 → JSON 응답 변환
 *
 * 4. CompanyControllerSample (이 파일)
 *    - 실제 사용 예시
 *    - 위 3개를 어떻게 조합하는지 보여줌
 *
 * ========================================
 * 실제 프로젝트에 적용하려면?
 * ========================================
 *
 * 1. Sample 파일들을 복사해서 "Sample" 제거
 *    - ApiResponseSample → ApiResponse
 *    - ResponseCodeSample → ResponseCode
 *    - GlobalExceptionHandlerSample → GlobalExceptionHandler
 *
 * 2. ResponseCode에 우리 프로젝트 코드 추가
 *    - 사용자의 API 명세에 맞춰 코드 추가
 *
 * 3. Controller에서 ApiResponse 사용
 *    - Mock 데이터 대신 실제 Service 호출
 *    - ApiResponse.success(코드, 데이터) 리턴
 *
 * 4. Service에서 예외 던지기
 *    - 에러 상황에서 throw new IllegalArgumentException()
 *    - GlobalExceptionHandler가 자동으로 처리
 */
