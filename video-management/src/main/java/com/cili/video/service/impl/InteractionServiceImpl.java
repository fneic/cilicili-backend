package com.cili.video.service.impl;

import com.cili.video.model.vo.InterActive;
import com.cili.video.service.InteractionActiveService;
import com.cili.video.service.InteractionService;
import com.cili.video.service.VideoInteractionService;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName InteractionServiceImpl
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/12 21:36
 **/
@Service
public class InteractionServiceImpl implements InteractionService {

    @Resource
    private InteractionActiveService interactionActiveService;
    @Resource
    private VideoInteractionService videoInteractionService;
    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    private static final String LOCK_NAME_PREFIX = "video:like:lock:";

    @Override
    public void like(Long vid) {
        Long uid = UserContextHold.getUid();
        //高并发下点赞重复问题
        String lockName = LOCK_NAME_PREFIX + uid;
        RLock lock = redissonClient.getLock(lockName);
        boolean isLock = lock.tryLock();
        //获取锁失败
        if(!isLock){
            return;
        }
        try{
            InterActive active = interactionActiveService.active(vid);
            boolean like = active.getIsLike();
            active.setIsLike(!like);
            interactionActiveService.setActive(vid,active);
            videoInteractionService.setLike(vid,!like);
        }finally {
            lock.unlock();
        }
    }
}
