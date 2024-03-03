package com.cili.video.job;

import com.cili.video.model.entity.InteractionActive;
import com.cili.video.model.entity.VideoInteraction;
import com.cili.video.model.vo.InterActive;
import com.cili.video.service.InteractionActiveService;
import com.cili.video.service.VideoInteractionService;
import com.cili.video.service.adapter.InteractionAdapter;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static com.cili.video.common.RedisKey.*;

/**
 * @ClassName VideoInteractionSync
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/13 0:41
 **/
@Component
@Slf4j
public class VideoInteractionSync {
    @Resource
    private CommonRedisTemplate commonRedisTemplate;
    @Resource
    private VideoInteractionService videoInteractionService;

    @Resource
    private InteractionActiveService interactionActiveService;

    @Scheduled(fixedDelay = 1,initialDelay = 1,timeUnit = TimeUnit.MINUTES)
    public void syncVideoInteractionRecordTask(){
        /**
         * 查询到同步任务后必须先删除，并且使用lua脚本保证原子性
         * 执行任务后再删除存在问题：很可能会删除新添加的同步任务
         */
        Set<Long> keys = commonRedisTemplate.getAndRemoveSet(VIDEO_INTERACTION_RECORD_SYNC, Long.class);
        if(keys.isEmpty()){
            return;
        }
        List<VideoInteraction> taskItems = new ArrayList<>();
        for (Long key : keys) {
            VideoInteraction videoInteraction = commonRedisTemplate.getHashObj(VIDEO_INTERACTION_RECORD + key, VideoInteraction.class);
            taskItems.add(videoInteraction);
        }
        boolean updateBatch = videoInteractionService.updateBatchById(taskItems);
        if(!updateBatch){
            /** 如果同步失败，将数据还原
             *
             */
            Object[] array = keys.toArray();
            commonRedisTemplate.addAllSet(VIDEO_INTERACTION_RECORD_SYNC,array);
            log.error("同步失败[task:{}，sum:{}]","视频点赞数等同步",keys.size());
        }else{
            log.info("同步成功[task:{}，sum:{}]","视频点赞数等同步",keys.size());
        }
    }

    @Scheduled(fixedDelay = 1,initialDelay = 1,timeUnit = TimeUnit.MINUTES)
    public void syncVideoInteractionActiveTask(){
        Set<String> keys = commonRedisTemplate.getAndRemoveSet(VIDEO_INTERACTION_ACTIVE_SYNC);
        if(keys.isEmpty()){
            return;
        }
        List<InteractionActive> taskItems = new ArrayList<>();
        for (String key : keys) {
            String[] props = key.split("-");
            InterActive interActive = commonRedisTemplate.getHashObj(VIDEO_INTERACTION_ACTIVE + key, InterActive.class);
            InteractionActive interactionActive = InteractionAdapter.interActive2PO(interActive, Long.parseLong(props[1]), Long.parseLong(props[0]));
            taskItems.add(interactionActive);
        }
        boolean savedBatch = interactionActiveService.updateBatchById(taskItems);
        if(!savedBatch){
            //如果同步失败，将数据还原
            Object[] array = keys.toArray();
            commonRedisTemplate.addAllSet(VIDEO_INTERACTION_ACTIVE_SYNC,array);
            log.error("同步失败[task:{}，sum:{}]","用户点赞、投币等同步",keys.size());
        }else{
            log.info("同步成功[task:{}，sum:{}]","用户点赞、投币等同步",keys.size());
        }
    }

}
