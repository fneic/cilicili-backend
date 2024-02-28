package com.cilicili.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author Zhou JunJie
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(T data) {
        this(StatusCode.SUCCESS.getCode(), data, StatusCode.SUCCESS.getMessage());
    }

    public BaseResponse() {
        this.code = StatusCode.SUCCESS.getCode();
        this.data = (T)"ok";
        this.message = StatusCode.SUCCESS.getMessage();
    }

    public BaseResponse(StatusCode errorCode) {
        this(errorCode.getCode(), (T)"fail", errorCode.getMessage());
    }
}
