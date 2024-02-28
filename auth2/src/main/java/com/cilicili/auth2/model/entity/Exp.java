package com.cilicili.auth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户等级经验表
 * @TableName exp
 */
@TableName(value ="exp")
@Data
public class Exp implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;


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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}