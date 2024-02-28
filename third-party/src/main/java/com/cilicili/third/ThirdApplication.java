package com.cilicili.third;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName ThirdApplication
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 12:07
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ThirdApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdApplication.class,args);
    }
}
