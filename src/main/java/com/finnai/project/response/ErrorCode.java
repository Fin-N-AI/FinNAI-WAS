package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 공통 에러 */
    INTERNAL_SERVER_ERROR(DomainCode.SERVER, ExceptionCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    /* Auth (Domain 2) */
    AUTHENTICATION_FAILED(DomainCode.AUTH, ExceptionCode.INVALID_INPUT_VALUE, HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    EMAIL_DUPLICATION(DomainCode.AUTH, ExceptionCode.DUPLICATE_RESOURCE, HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),

    /* User (Domain 3) */
    USER_NOT_FOUND(DomainCode.USER, ExceptionCode.NOT_FOUND, HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    /* Company (Domain 1) */
    COMPANY_NOT_FOUND(DomainCode.COMPANY, ExceptionCode.NOT_FOUND, HttpStatus.NOT_FOUND, "해당 기업을 찾을 수 없습니다."),
    COMPANY_ACCESS_DENIED(DomainCode.COMPANY, ExceptionCode.ACCESS_DENIED, HttpStatus.FORBIDDEN, "해당 기업 정보에 접근할 권한이 없습니다."),

    /* File (Domain 4) */
    FILE_NOT_FOUND(DomainCode.FILE, ExceptionCode.NOT_FOUND, HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
    ;





    private final DomainCode domainCode;
    private final ExceptionCode exceptionCode;
    private final HttpStatus httpStatus;
    private final String message;

    public int getCode(){
        return domainCode.getValue() * 100 + exceptionCode.getValue();
    }

}
