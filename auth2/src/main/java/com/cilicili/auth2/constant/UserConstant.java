package com.cilicili.auth2.constant;

/**
 * @ClassName UserConstant
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/9 13:36
 **/
public interface UserConstant {
    /**
     * 登录方式
     */
    Integer LOGIN_OF_PHONE = 0;
    Integer LOGIN_OF_ACCOUNT = 1;


    /**
     * 登录的用户
     */
    String USER_LOGIN_STATE_PREFIX = "user:login:";

    /**
     * redis中用户信息过期时间 day
     */
    int USER_STATE_REDIS_EXPIRE = 10;

    /**
     * 用户状态
     */
    Integer NORMAL = 1;
    Integer FORBID = 0;
}
