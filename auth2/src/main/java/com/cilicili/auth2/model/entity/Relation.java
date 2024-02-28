package com.cilicili.auth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户关系表
 * @TableName relation
 */
@TableName(value ="relation")
@Data
public class Relation implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关注者id
     */
    private Long follower;

    /**
     * 粉丝id
     */
    private Long following;

    public Relation(Long follower, Long following) {
        this.follower = follower;
        this.following = following;
    }

    public Relation(){}

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否注销账户（0-未删除 1-已删除（注销））
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}