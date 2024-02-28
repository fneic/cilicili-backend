package com.cilicili.auth2.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName UserUpdateDto
 * @Description 用户修改信息
 * @Author Zhou JunJie
 * @Date 2023/12/19 22:09
 **/
@Data
public class UserUpdateDto implements Serializable {
    private static final long serialVersionUID = -3682609147906772665L;
    /**
     * 姓名
     */
    private String nickName;


    /**
     * 头像地址，URL
     */
    private String avatar;


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


}
