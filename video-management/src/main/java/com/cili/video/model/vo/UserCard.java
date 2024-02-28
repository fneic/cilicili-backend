package com.cili.video.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserCard
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/11 15:09
 **/
@Data
public class UserCard implements Serializable {
    private static final long serialVersionUID = -2512480214660359829L;
    /**
     * id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像地址，URL
     */
    private String avatar;

    /**
     * 个人签名
     */
    private String profile;
}
