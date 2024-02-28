package com.cili.video.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 视频弹幕表
 * @TableName dm
 */
@TableName(value ="dm")
@Data
public class Dm implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 弹幕颜色
     */
    private String color;

    /**
     * 字体大小
     */
    private Integer fontsize;

    /**
     * 弹幕在视频中出现时间 ms
     */
    private Integer stamp;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}