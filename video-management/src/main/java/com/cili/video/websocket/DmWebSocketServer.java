package com.cili.video.websocket;

import com.cili.video.model.entity.Dm;
import com.cili.video.mq.MqService;
import com.cili.video.service.DmService;
import com.cilicili.common.utils.JsonUtils;
import com.cilicili.common.utils.JwtUtils;
import io.reactivex.Completable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName DmWebSocketServer
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/3 19:30
 **/
@Component
@ServerEndpoint("/dm/{vid}/{token}")
public class DmWebSocketServer {

    private static final Map<Long, Map<String, DmWebSocketServer>> ONLINE_CLIENTS_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, AtomicInteger> ONLINE_COUNT_MAP = new ConcurrentHashMap<>();

    private Map<String, DmWebSocketServer> room = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Session session;

    private String id;

    private Long uid;

    private Long vid;

    private static ApplicationContext APPLICATION_CONTEXT;

    public void setApplicationContext(ApplicationContext applicationContext){
        DmWebSocketServer.APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 通过视频id 会话id 获取会话
     * @param vid
     * @param sessionId
     * @return
     */
    public static Session getSession(Long vid,String sessionId){
        return ONLINE_CLIENTS_MAP.get(vid).get(sessionId).getSession();
    }

    private Session getSession(){
        return session;
    }

    @OnOpen
    public void connect(Session session, @PathParam("vid") Long vid,@PathParam(("token")) String token){
        Map<String, DmWebSocketServer> room = ONLINE_CLIENTS_MAP.get(vid);
        //获取当前房间的socket集合
        if(room == null){
            room = new ConcurrentHashMap<>();
            ONLINE_CLIENTS_MAP.put(vid,room);
        }
        this.room = room;
        this.id = session.getId();
        this.session = session;
        if(room.containsKey(id)){
            room.remove(id);
        }else{
            //统计在线人数
            AtomicInteger atomicInteger = ONLINE_COUNT_MAP.get(vid);
            if(atomicInteger==null){
                atomicInteger = new AtomicInteger(0);
                ONLINE_COUNT_MAP.put(vid,atomicInteger);
            }
            atomicInteger.getAndIncrement();
        }
        room.put(id,this);
        try{
            uid = JwtUtils.getUserByAccessToken(token).getId();
        }catch (Exception e){
            logger.info("用户：{}未登录",id);
        }
        this.vid = vid;
        logger.info("用户：{}连接，在线人数：{}",id,ONLINE_COUNT_MAP.get(vid).get());
    }

    @OnClose
    public void disconnect(){
        if(room.containsKey(this.id)){
            room.remove(this.id);
            ONLINE_COUNT_MAP.get(vid).getAndDecrement();
        }
        logger.info("用户：{}断开连接，在线人数：{}",id,ONLINE_COUNT_MAP.get(vid).get());
    }

    @OnMessage
    public void receive(String message,Session session) throws EncodeException, IOException {
        Msg msg = JsonUtils.toObject(message, Msg.class);
        if(msg.getType().equals("count")){
            int count = ONLINE_COUNT_MAP.get(vid).get();
            session.getBasicRemote().sendText(count + "");
        }else{
            sendDm(msg.getMessage());
        }
    }

    private void sendDm(String message) throws IOException {
        Dm dm = JsonUtils.toObject(message, Dm.class);
        if(uid == null){
            session.getBasicRemote().sendText("未登录");
            return;
        }
        dm.setUserId(uid);
        dm.setCreateTime(new Date());
        MqService mqService = APPLICATION_CONTEXT.getBean(MqService.class);
        Map<Object, Object> mqMessage = new HashMap<>(2);
        mqMessage.put("dm",dm);
        //群发
        for (DmWebSocketServer socketServer : room.values()) {
            mqMessage.put("sessionId",socketServer.session.getId());
            message = JsonUtils.toStr(mqMessage);
            //异步群发
            mqService.sendMessage("video.exchange",message,"dm");
        }
        DmService dmService = APPLICATION_CONTEXT.getBean(DmService.class);
        //保存弹幕到本地
        dmService.saveDm(dm);
        //保存弹幕到redis
        dmService.saveDmToRedis(dm);
    }

    @OnError
    public void error(Throwable error){

    }
    public void send(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
