package com.cili.video.service;

import com.cili.video.model.dto.VideoPublishDto;
import com.cili.video.model.vo.VideoPlayResp;

/**
 * @ClassName VideoService
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 0:08
 **/
public interface VideoService {
    /**
     * 发布视频
     * @param videoPublishDto 发布视频的相关的信息
     */
    void publish(VideoPublishDto videoPublishDto);

    VideoPlayResp getVideoPlayInfo(Long vid);
}
