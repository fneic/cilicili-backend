package com.cili.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName VideoApplication
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 22:41
 **/
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan("com.cili.video.mapper")
@EnableScheduling
@EnableFeignClients
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class,args);
    }
}
