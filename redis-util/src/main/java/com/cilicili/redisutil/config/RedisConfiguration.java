package com.cilicili.redisutil.config;

import com.cilicili.redisutil.common.CodeManage;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ClassName RedisConfiguration
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 0:05
 **/
@Configuration
public class RedisConfiguration {

    @Bean
    public CommonRedisTemplate commonRedisTemplate(StringRedisTemplate stringRedisTemplate){
        return new CommonRedisTemplate(stringRedisTemplate);
    }

    @Bean
    public CodeManage codeManage(CommonRedisTemplate commonRedisTemplate){
        return new CodeManage(commonRedisTemplate);
    }

}
