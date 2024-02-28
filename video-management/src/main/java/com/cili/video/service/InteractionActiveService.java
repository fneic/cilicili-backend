package com.cili.video.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cili.video.model.entity.InteractionActive;
import com.cili.video.model.vo.InterActive;

/**
* @author Zhou JunJie
* @description 针对表【interaction_active(用户互动状态表)】的数据库操作Service
* @createDate 2023-12-12 20:53:31
*/
public interface InteractionActiveService extends IService<InteractionActive> {

    /**
     * 获取用户互动信息
     * @param vid 视频id
     * @param uid 用户id
     * @return 互动信息
     */
    InteractionActive getInteractionActiveByDB(Long vid,Long uid);

    /**
     * 获取用户互动信息
     *
     * @param vid 视频id
     * @return 互动信息
     */
    InterActive active(Long vid);

    /**
     * 修改用户互动状态
     */

    void setActive(Long vid,InterActive interActive);

}
