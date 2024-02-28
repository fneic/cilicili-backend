package com.cili.video.mq;


import com.cilicili.redisutil.common.CommonRedisTemplate;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName VideoPlayConsumer
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/2/28 16:53
 **/
@Component
@Slf4j
public class VideoPlayConsumer {

    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @RabbitListener(queues = {"video.play.queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收到视频:{}",message);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = simpleDateFormat.format(new Date());
        //将该视频的播放量+1
        Boolean b = commonRedisTemplate.incrementZSet("video:play:" + day, message, 1d);
        if(!b){
            log.info("视频{}播放量统计失败",message);
            channel.basicReject(deliveryTag,false);
            return ;
        }
        channel.basicAck(deliveryTag,false);
    }
}
