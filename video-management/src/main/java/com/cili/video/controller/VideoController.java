package com.cili.video.controller;

import com.cili.video.annotation.NoLogin;
import com.cili.video.model.dto.VideoPublishDto;
import com.cili.video.model.vo.VideoPlayResp;
import com.cili.video.service.VideoService;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName VideoController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 0:03
 **/
@RestController
public class VideoController {
    @Resource
    private VideoService videoService;
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


}
