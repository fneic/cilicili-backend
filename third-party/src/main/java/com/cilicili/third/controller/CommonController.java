package com.cilicili.third.controller;

import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.common.utils.ResultUtils;
import com.cilicili.redisutil.common.CodeManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName CommonController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 11:56
 **/
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Resource
    private CodeManage codeManage;
    @GetMapping("/sms")
    public BaseResponse<?> sms(@RequestParam("phone") String phone){
        ParamsCheck.checkPhoneNumber(phone);
        Integer code = codeManage.generateMsgCode(phone);
        log.info("短信验证码申请：手机:{} code:{}",phone,code);
        //todo 调用短信服务发送验证码
        return ResultUtils.success(code);
    }

    @GetMapping("/sms/check")
    public BaseResponse<?> smsCheck(@RequestParam("phone") String phone,@RequestParam("code") Integer code){
        ParamsCheck.checkPhoneNumber(phone);
        codeManage.checkMsgCode(phone,code);
        return ResultUtils.success();
    }
}
