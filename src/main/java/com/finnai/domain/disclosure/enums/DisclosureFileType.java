package com.finnai.domain.disclosure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisclosureFileType {

    HTML("HTML"),
    PDF("PDF"),
    XBRL("XBRL");

    private final String value;

    public static DisclosureFileType fromValue(String value) {
        for (DisclosureFileType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DisclosureFileType: " + value);
    }
}
