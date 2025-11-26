package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainCode {
    SERVER(0),
    COMPANY(1),
    AUTH(2),
    USER(3),
    FILE(4);

    int value;


}
