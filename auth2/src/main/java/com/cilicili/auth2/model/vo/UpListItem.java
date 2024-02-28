package com.cilicili.auth2.model.vo;

import lombok.Data;

/**
 * @ClassName UpListItem
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 15:55
 **/
@Data
public class UpListItem {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 头像地址，URL
     */
    private String avatar;

    /**
     * 个人签名
     */
    private String sign;
}
