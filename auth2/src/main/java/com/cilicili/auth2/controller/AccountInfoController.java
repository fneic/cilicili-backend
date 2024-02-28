package com.cilicili.auth2.controller;

import com.cilicili.auth2.model.entity.AccountInfo;
import com.cilicili.auth2.model.vo.NavStat;
import com.cilicili.auth2.service.AccountInfoService;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName AccountInfoController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 22:02
 **/
@RestController
public class AccountInfoController {
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 返回当前用户的 粉丝数 关注数 动态数
     * @return
     */
    @GetMapping("/nav/stat")
    public BaseResponse<NavStat> state(){
        NavStat navStat = accountInfoService.state();
        return ResultUtils.success(navStat);
    }
}
