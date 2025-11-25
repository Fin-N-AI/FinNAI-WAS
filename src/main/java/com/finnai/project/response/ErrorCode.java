package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    COMPANY_NOT_FOUND(DomainCode.COMPANY, ExceptionCode.NOT_FOUND, "해당 기업을 찾을 수 없습니다.");

    private DomainCode domainCode;
    private ExceptionCode exceptionCode;
    private String message;

    public int getCode(){
        return domainCode.getValue() * 100 + exceptionCode.getValue();
    }

}
