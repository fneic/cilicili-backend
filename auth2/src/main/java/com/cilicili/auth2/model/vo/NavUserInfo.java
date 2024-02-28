package com.cilicili.auth2.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName NavUserInfo
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/17 0:40
 **/
@Data
public class NavUserInfo extends UpInfo implements Serializable {

    private static final long serialVersionUID = 4747633445624569834L;

    /**
     * 经验
     */
    private ExpInfo expInfo;

    /**
     * 硬币数
     */
    private Integer coins;
}
