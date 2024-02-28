package com.cilicili.auth2.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName NavStat
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 22:06
 **/
@Data
@Builder
public class NavStat implements Serializable {
    private static final long serialVersionUID = 140297503242442058L;

    /**
     * 粉丝数
     */
    private Integer follower;

    /**
     * 关注数
     */
    private Integer following;

    /**
     * 动态数
     */
    private Integer dynamic;
}
