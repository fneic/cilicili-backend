package com.cilicili.common.resp;

import lombok.Getter;

/**
 * 自定义错误码
 *
 * @author Zhou JunJie
 */
@Getter
public enum StatusCode {

    SUCCESS(200, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作错误"),
    CODE_EXPIRE(30001, "验证码已过期"),
    CODE_NOT_CONSISTENT(30002,"验证码错误"),
    USER_REGISTER_FAILED(30003,"注册失败"),
    LOGIN_FAILED(30004,"登陆失败"),
    CODE_APPLY_FAILED(30005,"验证码申请失败"),
    ACCESS_TOKEN_EXPIRE(30006,"access_token 已过期"),
    DATA_VERIFY_FILED(30007,"数据校验失败"),
    OPERATION_FAILED(30008,"请求操作失败"),
    ACCOUNT_FORBID(30009,"账号已被封禁"),
    FILE_TYPE_NOT_SUPPORTED(30010,"文件格式不支持");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode statusCodeOfCode(int code){
        for (StatusCode value : StatusCode.values()) {
            if(value.code == code){
                return value;
            }
        }
        return null;
    }

}
