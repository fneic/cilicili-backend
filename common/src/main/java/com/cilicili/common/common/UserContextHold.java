package com.cilicili.common.common;

import com.cilicili.common.model.SafeUser;

/**
 * @ClassName UserStore
 * @Description 存放登录用户信息
 * @Author Zhou JunJie
 * @Date 2023/11/15 16:25
 **/
public class UserContextHold {
    private UserContextHold(){}

    private static final ThreadLocal<SafeUser> LOGIN_USER_MAP = new ThreadLocal<>();

    public static void keepLoginUser(SafeUser safeUser){
        LOGIN_USER_MAP.set(safeUser);
    }

    public static SafeUser getCurrentUser(){
        return LOGIN_USER_MAP.get();
    }

    public static void removeUser(){
        LOGIN_USER_MAP.remove();
    }

    public static Long getUid(){
        return LOGIN_USER_MAP.get().getId();
    }

}
