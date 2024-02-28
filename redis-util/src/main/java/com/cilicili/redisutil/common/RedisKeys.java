package com.cilicili.redisutil.common;

/**
 * @author Zhou JunJie
 */
public interface RedisKeys {
    /**
     * 短信验证码
     */
    String MSG_CODE_PREFIX = "code:msg:";
    /**
     * 图形验证码
     */
    String IMG_CODE_PREFIX = "code:img:";

    /**
     * 请求频率限制
     */
    String REQUEST_INTERVAL_PREFIX = "request:interval:";

}
