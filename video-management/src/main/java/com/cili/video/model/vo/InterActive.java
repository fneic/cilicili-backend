package com.cili.video.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName InterActive
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/12 21:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterActive implements Serializable {


    /**
     * 是否点赞
     */
    private Boolean isLike;

    /**
     * 是否投币
     */
    private Boolean isCoin;

    /**
     * 是否收藏
     */
    private Boolean isCollect;

    /**
     * 是否收藏
     */
    private Boolean isPlay;


    private static final long serialVersionUID = -3164354699743235054L;
}
