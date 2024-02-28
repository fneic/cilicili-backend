package com.cilicili.auth2.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserloginDto
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 1:13
 **/
@Data
public class UserLoginDto implements Serializable {
    /**
     * 登录类型 0-手机号 1-账号密码
     */
    private int type;

    private String phone;

    private Integer msgCode;

    private String account;

    private String password;

    private String imgCode;


    private static final long serialVersionUID = 326242908378335872L;
}
