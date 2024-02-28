package com.cili.video.controller;

import com.cili.video.annotation.NoLogin;
import com.cili.video.model.entity.InteractionActive;
import com.cili.video.model.entity.VideoInteraction;
import com.cili.video.model.vo.InterActive;
import com.cili.video.service.InteractionActiveService;
import com.cili.video.service.InteractionService;
import com.cili.video.service.VideoInteractionService;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName InteractionController
 * @Description 视频互动
 * @Author Zhou JunJie
 * @Date 2023/12/11 22:44
 **/
@RestController
@RequestMapping("/interaction")
public class InteractionController {
    @Resource
    private VideoInteractionService videoInteractionService;
    @Resource
    private InteractionActiveService interactionActiveService;
    @Resource
    private InteractionService interactionService;

    /**
     * 获取视频的点赞数、投币数等
     * @param vid
     * @return
     */
    @GetMapping("/record")
    @NoLogin
    public BaseResponse<VideoInteraction> getInteractionData(@RequestParam Long vid){
        ParamsCheck.isNoNull(vid);
        VideoInteraction interaction = videoInteractionService.interaction(vid);
        return ResultUtils.success(interaction);
    }

    /**
     * 获取当前用户是否点赞、投币等信息
     * @param vid
     * @return
     */
    @GetMapping("/active")
    public BaseResponse<InterActive> getUserInteractionActive(@RequestParam Long vid){
        ParamsCheck.isNoNull(vid);
        InterActive interActive = interactionActiveService.active(vid);
        return ResultUtils.success(interActive);
    }

    /**
     * 视频点赞
     * @param vid
     * @return
     */
    @GetMapping("/like")
    public BaseResponse<?> like(@RequestParam Long vid){
        ParamsCheck.isNoNull(vid);
        interactionService.like(vid);
        return ResultUtils.success();
    }
}
