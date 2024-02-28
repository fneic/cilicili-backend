package com.cilicili.common.exception;


import com.cilicili.common.resp.StatusCode;

/**
 * 抛异常工具类
 *
 * @author Zhou JunJie
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, StatusCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, StatusCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }

    /**
     * 抛异常
     *
     * @param errorCode
     * @param message
     */
    public static void throwException(StatusCode errorCode, String message) {
        throw new BusinessException(errorCode, message);
    }

    /**
     * 抛异常
     *
     * @param errorCode
     */
    public static void throwException(StatusCode errorCode) {
        throw new BusinessException(errorCode);
    }

    public static void throwIfNull(Object object,StatusCode errorCode, String message) {
        if(object == null){
            throw new BusinessException(errorCode, message);
        }
    }

    public static void throwIfNull(Object object,StatusCode errorCode) {
        if(object == null){
            throw new BusinessException(errorCode);
        }
    }
}
