package com.cilicili.auth2.controller;

import com.cilicili.auth2.annotation.NoLogin;
import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.dto.UserUpdateDto;
import com.cilicili.auth2.model.vo.*;
import com.cilicili.auth2.service.UserService;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.JwtUtils;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName UserController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 1:05
 **/
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/user/login")
    @ApiOperation("用户登录")
    @NoLogin
    public BaseResponse<Tokens> login(@RequestBody UserLoginDto userLoginDto){
        ParamsCheck.isNoNull(userLoginDto);
        Tokens tokens = userService.login(userLoginDto);
        return ResultUtils.success(tokens);
    }

    @GetMapping("/user/current")
    @ApiOperation("当前用户信息")
    public BaseResponse<UserBaseInfo> getCurrentUser(){
        UserBaseInfo userBaseInfo = userService.currentUser();
        return ResultUtils.success(userBaseInfo);
    }

    @GetMapping("/user/refresh")
    @NoLogin
    public BaseResponse<Tokens> refreshToken(@RequestParam("refreshToken") String refreshToken){
        ParamsCheck.isNoNull(refreshToken);
        //获取用户id
        Long uid = JwtUtils.getUidByRefreshToken(refreshToken);
        Tokens tokens = userService.refresh(uid);
        return ResultUtils.success(tokens);
    }

    @GetMapping("/user/info")
    @NoLogin
    public BaseResponse<UpInfo> userInfo(@RequestParam("uid") Long uid){
        UpInfo upInfo =  userService.upInfo(uid);
        return ResultUtils.success(upInfo);
    }

    @GetMapping("/user/nav")
    public BaseResponse<NavUserInfo> navUserInfo(){
        NavUserInfo navUserInfo = userService.userInfo();
        return ResultUtils.success(navUserInfo);
    }

    @GetMapping("/card")
    @NoLogin
    public BaseResponse<UpCard> getUpCard(@RequestParam("uid") Long uid){
        UpCard upCard = userService.getUpCard(uid);
        return ResultUtils.success(upCard);
    }

    @PostMapping("/user/update")
    public BaseResponse<?> updateUser(@RequestBody UserUpdateDto userUpdateDto){
        ParamsCheck.isNoNull(userUpdateDto);
        userService.updateUserInfo(userUpdateDto);
        return ResultUtils.success();
    }

    @GetMapping("/account")
    public BaseResponse<UserAccount> account(){
        UserAccount userAccount =  userService.getAccount();
        return ResultUtils.success(userAccount);
    }

    @PostMapping("/account/update")
    public BaseResponse<?> accountUpdate(@RequestBody UserAccount userAccount){
        ParamsCheck.isNoNull(userAccount);
        userService.updateAccount(userAccount);
        return ResultUtils.success();
    }
}
