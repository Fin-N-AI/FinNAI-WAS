package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    NOT_FOUND(1),
    ACCESS_DENIED(2);

    private int value;

}
