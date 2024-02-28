package com.cili.video.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户互动状态表
 * @author Zhou JunJie
 * @TableName interaction_active
 */
@TableName(value ="interaction_active")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteractionActive implements Serializable {
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
     * 是否点赞
     */
    private Integer isLike;

    /**
     * 是否投币
     */
    private Integer isCoin;

    /**
     * 是否收藏
     */
    private Integer isCollect;

    /**
     * 是否看过
     */
    private Integer isPlay;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public static InteractionActive defaultActive(){
        InteractionActive interactionActive = new InteractionActive();
        interactionActive.setIsCoin(0);
        interactionActive.setIsLike(0);
        interactionActive.setIsCollect(0);
        return interactionActive;
    }
}