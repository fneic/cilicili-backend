package com.cili.video.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 视频基本信息表
 * @TableName video_base_info
 */
@TableName(value ="video_base_info")
@Data
@Builder
public class VideoBaseInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频媒体信息
     */
    private Long mid;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 主分区
     */
    private Long mainPartition;

    /**
     * 子分区
     */
    private Long subPartition;

    /**
     * 标签
     */
    private String tags;

    /**
     * 简介
     */
    private String profile;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 上传时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除（0-未删除 1-已删除）
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}