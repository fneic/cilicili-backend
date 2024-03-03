package com.cili.video.mq;


import cn.hutool.core.bean.BeanUtil;
import com.cili.video.model.entity.Dm;
import com.cili.video.remote.AuthServiceApi;
import com.cili.video.websocket.DmWebSocketServer;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.JsonUtils;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private AuthServiceApi authServiceApi;

    //异步统计播放量
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

    //异步推送到feed流
    @RabbitListener(queues = {"video.publish.queue"}, ackMode = "MANUAL")
    public void pushVideo2Fans(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收到博主发送的视频:{}",message);
        String[] split = message.split("-");
        Long upId = Long.parseLong(split[0]);
        Long vid = Long.parseLong((split[1]));
        //通过up id查询粉丝列表，进行推送
        BaseResponse<List<Long>> resp = authServiceApi.getFollowings(upId);
        if(resp.getCode() != StatusCode.SUCCESS.getCode()){
            //todo 请求失败，进行重试
            channel.basicReject(deliveryTag,false);
            return ;
        }
        List<Long> followings = resp.getData();
        for (Long following : followings) {
            //推送到粉丝频道
            commonRedisTemplate.pushList(following.toString(),vid.toString());
        }
    }

    @RabbitListener(queues = {"video.dm.queue"}, ackMode = "MANUAL")
    public void pushDm2User(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        Map map = JsonUtils.toObject(message, Map.class);
        String sessionId = (String) map.get("session");
        String dm_str = (String) map.get("dm");
        Dm dm = JsonUtils.toObject(dm_str, Dm.class);
        Session session = DmWebSocketServer.getSession(dm.getVideoId(), sessionId);
        if(session.isOpen()){
            try {
                //向该会话发送弹幕
                session.getBasicRemote().sendObject(dm);
                channel.basicAck(deliveryTag,false);
            } catch (EncodeException e) {
                log.error("弹幕[{}]-sender:{} -> sessionId:{},发送失败",dm.getId(),dm.getUserId(),session);
                channel.basicReject(deliveryTag,false);
            }
        }
    }


}
