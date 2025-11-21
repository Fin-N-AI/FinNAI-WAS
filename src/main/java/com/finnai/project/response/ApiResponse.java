package com.finnai.project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

//    // 성공 응답 (데이터 있음)
//    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data) {
//        return new ApiResponse<>(
//            responseCode.getCode(),
//            responseCode.getMessage(),
//            data
//        );
//    }
//
//    // 성공 응답 (데이터 없음)
//    public static <T> ApiResponse<T> success(ResponseCode responseCode) {
//        return new ApiResponse<>(
//            responseCode.getCode(),
//            responseCode.getMessage(),
//            null
//        );
//    }

//    // 실패 응답
//    public static <T> ApiResponse<T> error(ResponseCode responseCode) {
//        return new ApiResponse<>(
//            responseCode.getCode(),
//            responseCode.getMessage(),
//            null
//        );
//    }
//
//    // 실패 응답 (커스텀 메시지)
//    public static <T> ApiResponse<T> error(ResponseCode responseCode, String customMessage) {
//        return new ApiResponse<>(
//            responseCode.getCode(),
//            customMessage,
//            null
//        );
//    }
}
