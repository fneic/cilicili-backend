package com.cili.video.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MqService {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendMessage(){
        String exchange = "myExchange.fanout";
        String message = "hello,world!";
        rabbitTemplate.convertAndSend(exchange,"",message);
    }

    public void sendMessage(String exchange,String message,String routingKey){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}