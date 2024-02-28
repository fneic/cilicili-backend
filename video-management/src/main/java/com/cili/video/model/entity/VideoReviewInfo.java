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
 * 视频审核表
 * @TableName video_review_info
 */
@TableName(value ="video_review_info")
@Data
@Builder
public class VideoReviewInfo implements Serializable {
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
     * 审核状态 0-审核中 1-审核结束
     */
    private Integer reviewStatus;

    /**
     * 审核结果 0-未知 1-通过 2-未通过
     */
    private Integer reviewResult;

    /**
     * 审核意见
     */
    private String reviewComments;

    /**
     * 申请时间
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