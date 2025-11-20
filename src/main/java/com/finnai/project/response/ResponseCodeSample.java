package com.finnai.project.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ========================================
 * ResponseCodeSample - 모든 응답 코드 모음
 * ========================================
 *
 * [역할]
 * - 성공/실패 응답 코드를 한 곳에서 관리
 * - T(True) prefix: 성공
 * - F(False) prefix: 실패
 *
 * [코드 규칙]
 * T-도메인-순번: 성공 (예: T-COMPANY-001)
 * F-도메인-HTTP상태코드: 실패 (예: F-COMPANY-404)
 *
 * [사용 위치]
 * - Controller에서 ApiResponseSample 만들 때 사용
 * - GlobalExceptionHandlerSample에서 에러 응답 만들 때 사용
 *
 * [전체 흐름]
 * 1. Controller에서 처리 성공
 * 2. ResponseCodeSample.COMPANY_DETAIL_SUCCESS 선택
 * 3. ApiResponseSample.success(코드, 데이터) 호출
 * 4. Client에게 T-COMPANY-001 응답
 *
 * [도메인 구분]
 * - COMPANY: 기업 관련
 * - AUTH: 인증 관련
 * - USER: 사용자 관련
 * - FILE: 파일 관련
 */
@Getter
@RequiredArgsConstructor
public enum ResponseCodeSample {

    // ========================================
    // COMPANY 도메인 (기업 정보)
    // ========================================

    // 성공 코드
    T_COMPANY_LIST_SUCCESS("T-COMPANY-001", "기업 목록 조회 성공"),
    T_COMPANY_DETAIL_SUCCESS("T-COMPANY-002", "기업 상세 정보 조회 성공"),
    T_COMPANY_OVERVIEW_SUCCESS("T-COMPANY-003", "기업 개요 조회 성공"),
    T_COMPANY_FINANCIAL_SUCCESS("T-COMPANY-004", "기업 재무정보 조회 성공"),
    T_COMPANY_NEWS_SUCCESS("T-COMPANY-005", "기업 뉴스 조회 성공"),
    T_COMPANY_REPORT_SUCCESS("T-COMPANY-006", "기업 보고서 조회 성공"),
    T_COMPANY_INDICATOR_SUCCESS("T-COMPANY-007", "주식 지표 조회 성공"),
    T_COMPANY_WATCHLIST_ADD_SUCCESS("T-COMPANY-008", "관심기업 등록 성공"),

    // 실패 코드
    F_COMPANY_NOT_FOUND("F-COMPANY-404", "존재하지 않는 기업입니다"),
    F_COMPANY_SERVER_ERROR("F-COMPANY-500", "기업 정보 조회 중 오류가 발생했습니다"),

    // ========================================
    // AUTH 도메인 (인증)
    // ========================================

    // 성공 코드
    T_AUTH_SIGNUP_SUCCESS("T-AUTH-001", "회원가입 성공"),
    T_AUTH_LOGIN_SUCCESS("T-AUTH-002", "로그인 성공"),
    T_AUTH_REFRESH_SUCCESS("T-AUTH-003", "토큰 재발급 성공"),
    T_AUTH_PASSWORD_VERIFY_SUCCESS("T-AUTH-004", "비밀번호 인증 성공"),
    T_AUTH_PASSWORD_CHANGE_SUCCESS("T-AUTH-005", "비밀번호 변경 성공"),

    // 실패 코드
    F_AUTH_INVALID_CREDENTIALS("F-AUTH-401", "아이디 또는 비밀번호가 일치하지 않습니다"),
    F_AUTH_UNAUTHORIZED("F-AUTH-401", "인증이 필요합니다"),
    F_AUTH_TOKEN_EXPIRED("F-AUTH-401", "토큰이 만료되었습니다"),
    F_AUTH_DUPLICATE_EMAIL("F-AUTH-409", "이미 존재하는 이메일입니다"),

    // ========================================
    // USER 도메인 (사용자)
    // ========================================

    // 성공 코드
    T_USER_INFO_SUCCESS("T-USER-001", "회원정보 조회 성공"),
    T_USER_UPDATE_SUCCESS("T-USER-002", "회원정보 수정 성공"),
    T_USER_DELETE_SUCCESS("T-USER-003", "회원 탈퇴 성공"),
    T_USER_BOOKMARK_LIST_SUCCESS("T-USER-004", "북마크 목록 조회 성공"),
    T_USER_BOOKMARK_DELETE_SUCCESS("T-USER-005", "북마크 삭제 성공"),

    // 실패 코드
    F_USER_NOT_FOUND("F-USER-404", "존재하지 않는 사용자입니다"),
    F_USER_FORBIDDEN("F-USER-403", "권한이 없습니다"),

    // ========================================
    // FILE 도메인 (파일)
    // ========================================

    // 성공 코드
    T_FILE_UPLOAD_SUCCESS("T-FILE-001", "파일 업로드 성공"),
    T_FILE_INSIGHT_SUCCESS("T-FILE-002", "파일 분석 성공"),

    // 실패 코드
    F_FILE_NOT_FOUND("F-FILE-404", "파일을 찾을 수 없습니다"),
    F_FILE_SIZE_EXCEED("F-FILE-400", "파일 크기가 너무 큽니다"),
    F_FILE_INVALID_FORMAT("F-FILE-400", "지원하지 않는 파일 형식입니다"),

    // ========================================
    // COMMON 도메인 (공통)
    // ========================================
    F_INVALID_INPUT("F-COMMON-400", "잘못된 요청입니다"),
    F_SERVER_ERROR("F-COMMON-500", "서버 오류가 발생했습니다");

    private final String code;       // 응답 코드 (예: T-COMPANY-001)
    private final String message;    // 응답 메시지 (예: "기업 목록 조회 성공")
}

/*
 * ========================================
 * 사용 예시
 * ========================================
 *
 * [Controller에서 사용]
 *
 * @GetMapping("/{id}/summary")
 * public ApiResponseSample<?> getCompanySummary(@PathVariable Long id) {
 *     CompanyDto data = companyService.getSummary(id);
 *
 *     // ResponseCodeSample에서 성공 코드 선택
 *     return ApiResponseSample.success(
 *         ResponseCodeSample.T_COMPANY_DETAIL_SUCCESS,  // 이 enum 값 사용!
 *         data
 *     );
 * }
 *
 * [GlobalExceptionHandlerSample에서 사용]
 *
 * @ExceptionHandler(CompanyNotFoundException.class)
 * public ApiResponseSample<?> handleNotFound() {
 *     // ResponseCodeSample에서 실패 코드 선택
 *     return ApiResponseSample.error(
 *         ResponseCodeSample.F_COMPANY_NOT_FOUND  // 이 enum 값 사용!
 *     );
 * }
 *
 * ========================================
 * Enum이란?
 * ========================================
 *
 * - 상수들의 집합을 정의하는 특별한 클래스
 * - 미리 정의된 값만 사용 가능 (오타 방지)
 * - T_COMPANY_LIST_SUCCESS 같은 값들이 enum 상수
 *
 * [일반 변수와 비교]
 * String code = "T-COMPANY-001";  // 오타 가능, 관리 어려움
 * ResponseCodeSample.T_COMPANY_LIST_SUCCESS  // 자동완성, 오타 불가능
 *
 * ========================================
 * 클래스 관계도
 * ========================================
 *
 * ResponseCodeSample (이 Enum)
 *    ↓ 사용됨
 * ApiResponseSample에서 code, message 가져감
 *    ↓
 * Controller가 리턴
 *    ↓
 * Client에게 JSON 응답
 */
