package com.cili.video.service.adapter;

import com.cili.video.model.entity.InteractionActive;
import com.cili.video.model.entity.VideoInteraction;
import com.cili.video.model.vo.InterActive;

/**
 * @ClassName InteractionAdapter
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/12 21:15
 **/
public class InteractionAdapter {
    private InteractionAdapter(){}

    public static InterActive interactionActive2VO(InteractionActive interactionActive){
        Integer isCoin = interactionActive.getIsCoin();
        Integer isLike = interactionActive.getIsLike();
        Integer isCollect = interactionActive.getIsCollect();
        Integer isPlay = interactionActive.getIsPlay();
        return InterActive.builder().isCoin(isCoin > 0).isLike(isLike > 0).isCollect(isCollect > 0).isPlay(isPlay>0).build();
    }

    public static InteractionActive interActive2PO(InterActive interActive,Long uid,Long vid){
        Boolean isCoin = interActive.getIsCoin();
        Boolean isCollect = interActive.getIsCollect();
        Boolean isLike = interActive.getIsLike();
        Boolean isPlay = interActive.getIsPlay();
        return InteractionActive.builder().userId(uid).videoId(vid).isCoin(Boolean.TRUE.equals(isCoin)?1:0).isCollect(Boolean.TRUE.equals(isCollect)?1:0).isLike(isLike?1:0)
                .isPlay(Boolean.TRUE.equals(isPlay)?1:0).build();
    }
}
