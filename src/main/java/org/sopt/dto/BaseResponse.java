package org.sopt.dto;

import org.sopt.code.ErrorCode;
import org.sopt.code.SuccessCode;

public record BaseResponse<T>(int code, T data, String message) {

    public static <T> BaseResponse<T> success(SuccessCode code, T data){
        return new BaseResponse<>(code.getCode(), data, code.getMessage());
    }

    public static BaseResponse<Void> success(SuccessCode code){
        return new BaseResponse<>(code.getCode(), null, code.getMessage());
    }

    public static BaseResponse<Void> fail(ErrorCode code){
        return new BaseResponse<>(code.getCode(), null, code.getMessage());
    }
}
