package com.cili.video.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 视频互动表
 * @TableName video_interaction
 */
@TableName(value ="video_interaction")
@Data
public class VideoInteraction implements Serializable {
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
     * 点赞数
     */
    private Long likes;

    /**
     * 投币数
     */
    private Long coin;

    /**
     * 收藏数
     */
    private Long collect;

    /**
     * 转发数
     */
    private Long share;

    /**
     * 播放数
     */
    private Long play;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}