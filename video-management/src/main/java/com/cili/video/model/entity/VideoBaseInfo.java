package com.cili.video.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 视频基本信息表
 * @TableName video_base_info
 */
@TableName(value ="video_base_info")
@Data
@Builder
@Document(indexName = "videos")
public class VideoBaseInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @Id
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
    @Field(type = FieldType.Text)
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
    @Field(type = FieldType.Text)
    private String tags;

    /**
     * 简介
     */
    @Field(type = FieldType.Text)
    private String profile;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 上传时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;

    /**
     * 是否删除（0-未删除 1-已删除）
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}