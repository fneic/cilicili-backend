package com.cilicili.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SafeUser
 * @Description 脱敏用户信息
 * @Author Zhou JunJie
 * @Date 2023/11/28 1:45
 **/
@Data
public class SafeUser implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户账户信息
     */
    private Long accountInfoId;

    /**
     * 经验
     */
    private Long expId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;


    /**
     * 头像地址，URL
     */
    private String avatar;

    /**
     * 电话号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人签名
     */
    private String sign;

    /**
     * 生日
     */
    private Date birth;


    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 0-禁用 1-正常
     */
    private Integer status;


    /**
     * 创建时间
     */
    private Date createTime;


    private static final long serialVersionUID = -8204455117354523671L;
}
