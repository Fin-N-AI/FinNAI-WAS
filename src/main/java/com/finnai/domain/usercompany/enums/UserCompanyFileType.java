package com.finnai.domain.usercompany.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserCompanyFileType {

    PDF("PDF"),
    TXT("TXT"),
    XML("XML"),
    HTML("HTML"),
    DOCX("DOCX"),
    IMAGE("IMAGE");

    private final String value;

    public static UserCompanyFileType fromValue(String value) {
        for (UserCompanyFileType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UserCompanyFileType: " + value);
    }
}
