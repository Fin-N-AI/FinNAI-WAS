package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    INTERNAL_ERROR(0),
    NOT_FOUND(1),
    ACCESS_DENIED(2),
    INVALID_INPUT_VALUE(3),
    DUPLICATE_RESOURCE(4); // required를 작성안하거나, 유효하지 않은 값


    private int value;

}
