package org.sopt.dto;

import org.sopt.code.ErrorCode;
import org.sopt.code.SuccessCode;

public record ApiResponse<T>(int code, T data, String message) {

    public static <T> ApiResponse<T> success(SuccessCode code, T data){
        return new ApiResponse<>(code.getCode(), data, code.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessCode code){
        return new ApiResponse<>(code.getCode(), null, code.getMessage());
    }

    public static <T> ApiResponse<T> fail(ErrorCode code){
        return new ApiResponse<>(code.getCode(), null, code.getMessage());
    }
}
