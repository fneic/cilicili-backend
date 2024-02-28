package com.cilicili.auth2.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserShowInfo
 * @Description 传给前端显示的用户信息
 * @Author Zhou JunJie
 * @Date 2023/12/16 0:59
 **/
@Data
public class UserBaseInfo implements Serializable {

    private static final long serialVersionUID = -4004313616123175725L;

    /**
     * id
     */
    private Long id;

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
    private String birth;


    /**
     * 性别 0-未知 1-男 2-女
     */
    private String gender;


}
