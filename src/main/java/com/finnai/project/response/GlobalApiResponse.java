package com.finnai.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalApiResponse<T> {

    private boolean isSuccess;
    private String code;
    private String message;
    private T data;


    /**
     * {
     *     isSuccess : ture,
     *     code : 200,
     *     message : 조회 성공
     *     data : {
     *         "mydata" : "내 데이터입니다"
     *     }
     * }
     *
     * */
    //    // 성공 응답 (데이터 있음)
    public static <T> GlobalApiResponse<T> success(SuccessCode successCode, T data) {
        return new GlobalApiResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                data
        );
    }

    // 성공 응답 (데이터 없음)
    public static <T> GlobalApiResponse<T> success(SuccessCode successCode) {
        return new GlobalApiResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                null
        );
    }

    // 실패 응답
    public static <T> GlobalApiResponse<T> error(ErrorCode successCode) {
        return new GlobalApiResponse<>(
                false,
                successCode.getCode(),
                successCode.getMessage(),
                null
        );
    }

    // 실패 응답 (커스텀 메시지)
    public static <T> GlobalApiResponse<T> error(ErrorCode successCode, String customMessage) {
        return new GlobalApiResponse<>(
                false,
                successCode.getCode(),
                customMessage,
                null
        );
    }
}
