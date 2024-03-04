package com.cili.video.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

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
        Message messageBean = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)//持久化
                .build();
        rabbitTemplate.convertAndSend(exchange,routingKey,messageBean);
    }
}