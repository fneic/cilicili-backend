package com.cili.video.service.impl;

import com.cili.video.mq.MqService;
import com.cili.video.model.dto.VideoPublishDto;
import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.model.entity.VideoMediaInfo;
import com.cili.video.model.entity.VideoReviewInfo;
import com.cili.video.model.vo.VideoPlayResp;
import com.cili.video.service.*;
import com.cili.video.service.adapter.VideoAdapter;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * @ClassName VideoServiceImpl
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 0:08
 **/
@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private PartitionInfoService partitionInfoService;
    @Resource
    private VideoBaseInfoService videoBaseInfoService;

    @Resource
    private VideoMediaInfoService videoMediaInfoService;

    @Resource
    private VideoReviewInfoService videoReviewInfoService;

    @Resource
    private VideoInteractionService videoInteractionService;

    @Resource
    private MqService mqService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void publish(VideoPublishDto videoPublishDto) {
        Long mainPartition = videoPublishDto.getMainPartition();
        Long subPartition = videoPublishDto.getSubPartition();
        //查询所选分区是否存在
        boolean partitionIsExist = partitionInfoService.partitionIsExist(mainPartition, subPartition);
        ThrowUtils.throwIf(!partitionIsExist, StatusCode.OPERATION_FAILED,"所选分区不存在");
        VideoBaseInfo videoBaseInfo = VideoAdapter.buildBaseInfo(videoPublishDto);

        //保存视频信息
        VideoMediaInfo videoMediaInfo = VideoAdapter.buildMediaInfo(videoPublishDto);
        boolean save1 = videoMediaInfoService.save(videoMediaInfo);
        ThrowUtils.throwIf(!save1,StatusCode.OPERATION_FAILED);

        //保存作品信息
        videoBaseInfo.setMid(videoMediaInfo.getId());
        boolean save = videoBaseInfoService.save(videoBaseInfo);
        ThrowUtils.throwIf(!save,StatusCode.OPERATION_FAILED);
        //todo 异步推送动态到粉丝
        //添加审核信息
        VideoReviewInfo videoReviewInfo = VideoReviewInfo.builder().videoId(videoBaseInfo.getId()).build();
        boolean save2 = videoReviewInfoService.save(videoReviewInfo);
        videoInteractionService.initial(videoBaseInfo.getId());
        ThrowUtils.throwIf(!save2,StatusCode.OPERATION_FAILED);
    }

    @Override
    public VideoPlayResp getVideoPlayInfo(Long vid) {
        VideoBaseInfo videoBaseInfo = videoBaseInfoService.getById(vid);
        ThrowUtils.throwIf(videoBaseInfo == null,StatusCode.NOT_FOUND_ERROR);
        VideoMediaInfo videoMediaInfo = videoMediaInfoService.getById(videoBaseInfo.getMid());
        ThrowUtils.throwIf(videoMediaInfo == null,StatusCode.NOT_FOUND_ERROR);
        // 第一次播放应该增加播放量 考虑异步实现
        mqService.sendMessage("video.exchange",vid.toString(),"video");
        return VideoAdapter.buildVideoPlayResp(videoBaseInfo,videoMediaInfo);
    }
}
