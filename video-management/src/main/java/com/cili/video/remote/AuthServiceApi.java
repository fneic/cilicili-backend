package com.cili.video.remote;

import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ResultUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ClassName AuthApi
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/1 21:12
 **/
@FeignClient("cilicili-auth2")
public interface AuthServiceApi {

    @GetMapping("/followings")
    BaseResponse<List<Long>> getFollowings(Long uid);
}
