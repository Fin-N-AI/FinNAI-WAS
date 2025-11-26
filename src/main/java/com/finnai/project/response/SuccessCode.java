package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SuccessCode {



    // company 관련 T1001~F1999
    SUCCESS(200, "조회 성공"),
    CREATED( 201, "생성 성공"),  // ← 세미콜론 필수!

    UPDATED(202, "수정 성공"),
    REMOVED( 203, "삭제 성공");  // ← 세미콜론 필수!


    // user 관련 T3001~F3999

    // file 관련 T4001~4999

    private int code;
    private String message;

}
