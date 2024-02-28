package com.cilicili.auth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @author Zhou JunJie
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码，非必须
     */
    private String password;

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
     * 微信登录id
     */
    private String openid;

    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 用户角色 0-普通用户 1-系统管理员
     */
    private Integer role;

    /**
     * 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否注销账户（0-未删除 1-已删除（注销））
     */
    private Integer isDelete;

    /**
     * 用户账户信息
     */
    private Long accountInfoId;

    /**
     * 经验
     */
    private Long expId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}