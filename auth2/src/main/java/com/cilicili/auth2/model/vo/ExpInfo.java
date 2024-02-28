package com.cilicili.auth2.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ExpInfo
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/17 0:42
 **/
@Data
public class ExpInfo implements Serializable {
    private static final long serialVersionUID = -6140007391239897469L;
    /**
     * 当前等级
     */
    private Integer currentLevel;

    /**
     * 当前经验值
     */
    private Integer currentExp;

    /**
     * 下一级所需经验
     */
    private Integer nextExp;
}
