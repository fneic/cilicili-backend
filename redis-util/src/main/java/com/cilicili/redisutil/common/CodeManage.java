package com.cilicili.redisutil.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;

import java.util.Objects;
import java.util.concurrent.TimeUnit;



/**
 * @ClassName MessageCodeManage
 * @Description 验证码管理器
 * @Author Zhou JunJie
 * @Date 2023/11/9 15:20
 **/
public class CodeManage {

    public CodeManage(CommonRedisTemplate commonRedisTemplate){
        this.commonRedisTemplate = commonRedisTemplate;
    }

    /**
     * 短信验证码有效期为 5min
     */
    private static final long MESSAGE_CODE_TTL = 5;
    /**
     * 短信限制发送频率为 60s
     */
    private static final long CODE_APPLY_INTERVAL = 60;

    /**
     * 图形验证码有效期为 5min
     */
    private static final long IMG_CODE_TTL = 5;
    
    private final CommonRedisTemplate commonRedisTemplate;
    
    
    

    /**
     * 生成6位验证码
     * @param phone 电话号
     * @return
     */
    public  Integer generateMsgCode(String phone){
        //请求间隔是否大于60s
        String intervalKey = RedisKeys.REQUEST_INTERVAL_PREFIX + phone;
        Integer isOften = commonRedisTemplate.get(intervalKey, Integer.class);
        ThrowUtils.throwIf(isOften != null, StatusCode.CODE_APPLY_FAILED,"请求过于频繁");
        //生成验证码
        Integer code = Integer.parseInt(RandomUtil.randomNumbers(6));
        //设置请求间隔
        Boolean isSetInterval = commonRedisTemplate.set(intervalKey, 1, CODE_APPLY_INTERVAL);
        ThrowUtils.throwIf(!isSetInterval,StatusCode.CODE_APPLY_FAILED);
        //保存验证码
        String codeKey = RedisKeys.MSG_CODE_PREFIX + phone;
        Boolean isSave = commonRedisTemplate.set(codeKey, code, MESSAGE_CODE_TTL, TimeUnit.MINUTES);
        ThrowUtils.throwIf(!isSave,StatusCode.CODE_APPLY_FAILED);
        return code;
    }

    /**
     * 生成4位验证码
     * @param account 账号
     * @return
     */
    public  ShearCaptcha generateImgCode(String account){
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(220, 100, 4, 4);
        String code = captcha.getCode();
        String codeKey = RedisKeys.IMG_CODE_PREFIX + account;
        Boolean isSave = commonRedisTemplate.set(codeKey, code, IMG_CODE_TTL, TimeUnit.MINUTES);
        ThrowUtils.throwIf(!isSave,StatusCode.CODE_APPLY_FAILED);
        return captcha;
    }

    /**
     * 校验图形验证码
     * @param key 账号
     * @param code 待校验的验证码
     * @return
     */
    public  void checkImgCode(String key,String code){
        String codeKey = RedisKeys.IMG_CODE_PREFIX + key;
        String realCode = commonRedisTemplate.get(codeKey, String.class);
        ThrowUtils.throwIf(realCode == null,StatusCode.CODE_EXPIRE);
        ThrowUtils.throwIf(!Objects.equals(code,realCode),StatusCode.CODE_NOT_CONSISTENT);
    }

    /**
     * 校验短信验证码
     * @param phone 电话号码
     * @param code 待校验的验证码
     * @return
     */
    public  void checkMsgCode(String phone,Integer code){
        String codeKey = RedisKeys.MSG_CODE_PREFIX + phone;
        Integer realCode = commonRedisTemplate.get(codeKey, Integer.class);
        ThrowUtils.throwIf(realCode == null,StatusCode.CODE_EXPIRE);
        ThrowUtils.throwIf(!Objects.equals(code,realCode),StatusCode.CODE_NOT_CONSISTENT);
    }
}
