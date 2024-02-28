package com.cili.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.mapper.VideoInteractionMapper;
import com.cili.video.model.entity.VideoInteraction;
import com.cili.video.service.VideoInteractionService;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import static com.cili.video.common.RedisKey.VIDEO_INTERACTION_RECORD;
import static com.cili.video.common.RedisKey.VIDEO_INTERACTION_RECORD_SYNC;

/**
* @author Zhou JunJie
* @description 针对表【video_interaction(视频互动表)】的数据库操作Service实现
* @createDate 2023-12-11 22:39:33
*/
@Service
public class VideoInteractionServiceImpl extends ServiceImpl<VideoInteractionMapper, VideoInteraction>
    implements VideoInteractionService {
    @Resource
    private CommonRedisTemplate commonRedisTemplate;


    @Override
    public VideoInteraction interaction(Long vid) {
        String key = VIDEO_INTERACTION_RECORD + vid;
        VideoInteraction videoInteraction = commonRedisTemplate.getHashObj(key, VideoInteraction.class);
        if(videoInteraction == null){
            videoInteraction = getInteractionByVid(vid);
            ThrowUtils.throwIf(videoInteraction==null, StatusCode.NOT_FOUND_ERROR);
            commonRedisTemplate.setHashAll(key,videoInteraction);
        }
        return videoInteraction;
    }

    @Override
    public void setLike(Long vid, boolean like) {
        String key = VIDEO_INTERACTION_RECORD + vid;
        Long step = like?1L:-1L;
        commonRedisTemplate.increaseHashItemByStep(key,"likes",step);
        //加入同步队列中
        addSyncQue(vid);
    }

    /**
     * 将更新的缓存结果加入待同步队列，进行增量同步
     * @param vid
     */
    private void addSyncQue(Long vid){
        commonRedisTemplate.addSet(VIDEO_INTERACTION_RECORD_SYNC,vid);
    }

    @Override
    public void initial(Long vid) {
        String key = VIDEO_INTERACTION_RECORD + vid;
        VideoInteraction videoInteraction = new VideoInteraction();
        videoInteraction.setVideoId(vid);
        //todo 可以先不写入数据库，redis会同步，但是这样需要一个初始化的videoInteraction
        boolean saved = save(videoInteraction);
        ThrowUtils.throwIf(!saved,StatusCode.OPERATION_FAILED);
        commonRedisTemplate.setHashAll(key,videoInteraction);
    }



    private VideoInteraction getInteractionByVid(Long vid){
        LambdaQueryWrapper<VideoInteraction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoInteraction::getVideoId,vid);
        return getOne(wrapper);
    }
}




