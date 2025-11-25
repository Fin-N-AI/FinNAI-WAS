package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DomainCode {
    COMPANY(1),
    AUTH(2);

    int value;

    DomainCode(int i) {
        this.value = i;
    }
}
