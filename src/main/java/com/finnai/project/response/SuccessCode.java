package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    SUCCESS(HttpStatus.OK, "조회 성공"),
    CREATED( HttpStatus.CREATED, "생성 성공"),  // ← 세미콜론 필수!

    UPDATED(HttpStatus.ACCEPTED, "수정 성공"),
    REMOVED(HttpStatus.NO_CONTENT, "삭제 성공");  // ← 세미콜론 필수!


    // user 관련 T3001~F3999

    // file 관련 T4001~4999

    private final HttpStatus httpStatus;
    private String message;

}
