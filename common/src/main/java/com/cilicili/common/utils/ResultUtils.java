package com.cilicili.common.utils;

import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.resp.StatusCode;

/**
 * 返回工具类
 *
 * @author Zhou JunJie
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>();
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(StatusCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(StatusCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), "fail", message);
    }
}
