package com.cili.video.service;

/**
 * @ClassName InteractionService
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/12 21:35
 **/
public interface InteractionService {

    /**
     * 点赞
     * @param vid 点赞的视频id
     */
    void like(Long vid);
}
