package com.cili.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.mapper.InteractionActiveMapper;
import com.cili.video.model.entity.InteractionActive;
import com.cili.video.model.vo.InterActive;
import com.cili.video.service.InteractionActiveService;
import com.cili.video.service.adapter.InteractionAdapter;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import static com.cili.video.common.RedisKey.*;

/**
* @author Zhou JunJie
* @description 针对表【interaction_active(用户互动状态表)】的数据库操作Service实现
* @createDate 2023-12-12 20:53:31
*/
@Service
public class InteractionActiveServiceImpl extends ServiceImpl<InteractionActiveMapper, InteractionActive>
    implements InteractionActiveService {

    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @Override
    public InteractionActive getInteractionActiveByDB(Long vid, Long uid) {
        LambdaQueryWrapper<InteractionActive> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InteractionActive::getUserId,uid).eq(InteractionActive::getVideoId,vid);
        return getOne(wrapper);
    }

    @Override
    public InterActive active(Long vid) {
        Long uid = UserContextHold.getCurrentUser().getId();
        String key = getActiveRedisKey(vid);
        InterActive interActive = commonRedisTemplate.getHashObj(key, InterActive.class);
        if(interActive == null){
            InteractionActive interactionActive = getInteractionActiveByDB(vid,uid);
            if(interactionActive == null){
                interActive = new InterActive(false,false,false,false);
            }else{
                interActive = InteractionAdapter.interactionActive2VO(interactionActive);
            }
            commonRedisTemplate.setHashAll(key,interActive);
        }
//        commonRedisTemplate.setHashAll(key,interActive);
        return interActive;
    }

    @Override
    public void setActive(Long vid, InterActive interActive) {
        String key = getActiveRedisKey(vid);
        commonRedisTemplate.setHashAll(key,interActive);
        addSyncQue(vid);
    }

    /**
     * 将更新的缓存结果加入待同步队列，进行增量同步
     * @param vid
     */
    private void addSyncQue(Long vid){
        Long uid = UserContextHold.getCurrentUser().getId();
        commonRedisTemplate.addSet(VIDEO_INTERACTION_ACTIVE_SYNC,vid + "-" + uid);
    }

    private String getActiveRedisKey(Long vid){
        Long uid = UserContextHold.getCurrentUser().getId();
        return VIDEO_INTERACTION_ACTIVE + vid + "-" + uid;
    }

}




