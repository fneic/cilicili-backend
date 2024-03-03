package com.cili.video.controller;

import com.cili.video.annotation.NoLogin;
import com.cili.video.es.VideoEsService;
import com.cili.video.model.dto.VideoPublishDto;
import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.model.vo.VideoPlayResp;
import com.cili.video.service.VideoService;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName VideoController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 0:03
 **/
@RestController
@Slf4j
public class VideoController {
    @Resource
    private VideoService videoService;
    @Resource
    private VideoEsService videoEsService;
    @PostMapping("/publish")
    public BaseResponse<?> publish(@RequestBody @Validated VideoPublishDto videoPublishDto){
        ParamsCheck.isNoNull(videoPublishDto);
        videoService.publish(videoPublishDto);
        return ResultUtils.success();
    }

    @GetMapping("/vdinfo")
    @NoLogin
    public BaseResponse<VideoPlayResp> getVideoInfo(@RequestParam Long vid){
        ParamsCheck.isNoNull(vid);
        VideoPlayResp videoPlayInfo = videoService.getVideoPlayInfo(vid);
        return ResultUtils.success(videoPlayInfo);
    }

    public BaseResponse<List<VideoBaseInfo>> getVideosByKeyWord(@RequestParam("keyword") String keyword,Integer pageNum,Integer pageSize){
        List<VideoBaseInfo> res = null;
        try {
            res = videoEsService.getVideosByKeyWord(keyword, pageNum, pageSize);
        } catch (IOException e) {
            ThrowUtils.throwException(StatusCode.SYSTEM_ERROR,"数据请求失败");
            log.error("视频关键字查询异常:keyword {} ,ex:{}",keyword,e);
        }
        return ResultUtils.success(res);
    }

}
