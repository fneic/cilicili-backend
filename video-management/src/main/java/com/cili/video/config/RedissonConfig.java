package com.cili.video.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedissonConfig
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/2/26 19:17
 **/
@Configuration
public class RedissonConfig {

    @Value("${redis.host}")
    String address;

    @Value("${redis.port}")
    String port;

    @Value("${redis.password}")
    String password;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + address + ":" + port).setPassword(password);
        return Redisson.create(config);
    }
}
