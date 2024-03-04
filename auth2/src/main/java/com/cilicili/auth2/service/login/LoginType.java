package com.cilicili.auth2.service.login;

import lombok.Getter;

import java.util.Objects;

/**
 * @ClassName LoginYype
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/4 23:09
 **/
public enum LoginType {
    PHONE_LOGIN("phoneLogin", 0),ACCOUNT_LOGIN("accountLogin", 1);

    @Getter
    private final String type;

    private final Integer code;

    LoginType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public static String codeToType(Integer code){
        for (LoginType value : LoginType.values()) {
            if(Objects.equals(value.code, code)){
                return value.type;
            }
        }
        return null;
    }

}
