package com.cilicili.auth2.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AccountInfo
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 14:27
 **/
@Data
public class UpInfo implements Serializable {

    private static final long serialVersionUID = 4146670604765812094L;

    /**
     * 粉丝数
     */
    private Integer follower;

    /**
     * 关注数
     */
    private Integer following;

    /**
     * 获赞数
     */
    private Integer likes;

    /**
     * 播放数
     */
    private Integer plays;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 用户基本信息
     */
    private UserBaseInfo userBaseInfo;
}
