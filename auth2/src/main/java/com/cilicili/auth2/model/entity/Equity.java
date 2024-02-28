package com.cilicili.auth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 权益等级表
 * @TableName equity
 */
@TableName(value ="equity")
@Data
public class Equity implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权益名
     */
    private String name;

    /**
     * 权益描述
     */
    private String description;

    /**
     * 所需等级
     */
    private Integer level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}