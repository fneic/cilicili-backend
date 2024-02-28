package com.cili.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cili.video.model.entity.VideoInteraction;



/**
* @author Zhou JunJie
* @description 针对表【video_interaction(视频互动表)】的数据库操作Service
* @createDate 2023-12-11 22:39:33
*/
public interface VideoInteractionService extends IService<VideoInteraction> {

    /**
     * 获取视频的点赞数、投币数等信息
     *
     * @param vid 视频id
     * @return
     */
    VideoInteraction interaction(Long vid);

    /**
     * 更改点赞数
     * @param vid
     * @param b
     */
    void setLike(Long vid, boolean b);

    /**
     * 新作品初始化数据
     *
     * @param vid
     */
    void initial(Long vid);

}
