package com.cilicili.auth2.controller;

import com.cilicili.auth2.model.vo.UpList;
import com.cilicili.auth2.service.RelationService;
import com.cilicili.common.model.PageRequest;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


/**
 * @ClassName RelationController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 1:46
 **/
@RestController
@RequestMapping("/relation")
public class RelationController {
    @Resource
    private RelationService relationService;

    /**
     * 关注up
     * @return
     */
    @GetMapping("/follow")
    public BaseResponse<?> follow(@RequestParam("fid") Long fid){
        ParamsCheck.isNoNull(fid);
        relationService.follow(fid);
        return ResultUtils.success();
    }

    /**
     * 是否关注up
     * @return
     */
    @GetMapping("/isfollow")
    public BaseResponse<Boolean> isFollow(@RequestParam("fid") Long fid){
        ParamsCheck.isNoNull(fid);
        Boolean follow = relationService.isFollow(fid);
        return ResultUtils.success(follow);
    }

    /**
     * 取消关注up
     * @return
     */
    @GetMapping("/unfollow")
    public BaseResponse<?> unFollow(@RequestParam("fid") Long fid){
        ParamsCheck.isNoNull(fid);
        relationService.unFollow(fid);
        return ResultUtils.success();
    }

    /**
     * 关注列表
     * @return
     */
    @PostMapping("/follows")
    public BaseResponse<UpList> follows(@RequestBody PageRequest pageRequest){
        UpList upList = relationService.listOfFollows(pageRequest);
        return ResultUtils.success(upList);
    }
}
