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
 * 视频媒体资源信息表
 * @TableName video_media_info
 */
@TableName(value ="video_media_info")
@Data
@Builder
public class VideoMediaInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     *  单位 byte
     */
    private Long videoSize;

//    /**
//     * 视频清晰度 1-360P流畅 2-480P清晰 3-720P高清 4-1080P高清 5-1080P高码率
//     */
//    private Integer videoClarity;

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