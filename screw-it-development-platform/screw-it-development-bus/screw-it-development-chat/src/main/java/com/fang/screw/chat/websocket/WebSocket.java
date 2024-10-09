package com.fang.screw.chat.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.fang.screw.chat.config.CustomSpringConfigurator;
import com.fang.screw.chat.mapper.MessageMapper;
import com.fang.screw.chat.utils.MessageEntityDecode;
import com.fang.screw.chat.utils.MessageEntityEncode;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.domain.po.MessagePO;
import com.fang.screw.domain.vo.MessageVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @FileName WebSocket
 * @Description 这段代码是WebSocket的端点定义，结合了Spring和WebSocket API的功能。
 * 它通过@ServerEndpoint注解指定了WebSocket的端点路径、消息的编码和解码器，以及自定义配置类CustomSpringConfigurator。
 * @Author yaoHui
 * @date 2024-10-08
 **/
@Component
@Slf4j
@ServerEndpoint("/websocket/{myUserId}")
public class WebSocket {

    /**
     * 与客户端的连接会话，需要通过他来给客户端发消息
     */
    private Session session;

    /**
     * 当前用户ID
     */
    private Integer userId;

    /**
     *  concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     *  虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     */
    private static final CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();

    /**
     *用来存在线连接用户信息
     */
    private static final ConcurrentHashMap<Integer,Session> sessionPool = new ConcurrentHashMap<Integer,Session>();

    private static MessageMapper messageMapper;

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper){
        WebSocket.messageMapper = messageMapper;
    }

    /**
     * 连接成功方法
     * @param session 连接会话
     * @param userId 用户编号
     */
    @OnOpen
    public void onOpen(Session session , @PathParam("myUserId") Integer userId){
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】 用户：" + userId + " 加入连接...");
        } catch (Exception e) {
            log.error("---------------WebSocket连接异常---------------");
        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(){
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】 用户："+ this.userId + " 断开连接...");
        } catch (Exception e) {
            log.error("---------------WebSocket断开异常---------------");
        }
    }

    @OnMessage
    public void onMessage(String body){
        try {
            //将Body解析
            MessageEntityDecode messageEntityDecode = new MessageEntityDecode();
            MessageVO messageVO = messageEntityDecode.decode(body);
            log.info(messageVO.toString());
            sendOneMessage(messageVO.getReceiveUser(),messageVO.getMessage());

            MessagePO messagePO = messageVO.transformPO();
            messageMapper.insert(messagePO);

//            JSONObject jsonObject = JSONObject.parseObject(body);
//            //获取目标用户地址
//            String targetUserId = jsonObject.getString("targetUserId");
//            //获取需要发送的消息
//            String message = jsonObject.getString("message");
//            jsonObject.put("userId" , userId);
//            if(userId.equals(targetUserId)){
//                sendMoreMessage(new String[]{targetUserId} ,  JSONObject.toJSONString(jsonObject));
//            }else{
//                sendMoreMessage(new String[]{userId , targetUserId} ,  JSONObject.toJSONString(jsonObject));
//            }
        } catch (Exception e) {
            log.error("---------------WebSocket消息异常---------------");
            e.printStackTrace();
        }
    }


    /**
     * 此为广播消息
     * @param message
     */
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("---------------WebSocket消息广播异常---------------");
            }
        }
    }

    /**
     * 单点消息
     * @param userId
     * @param message
     */
    public void sendOneMessage(Integer userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                MessageVO messageVO = new MessageVO();
                messageVO.setMessage(message);
                messageVO.setSendUser(this.userId);
                messageVO.setReceiveUser(userId);

                MessageEntityEncode messageEntityEncode = new MessageEntityEncode();
                session.getAsyncRemote().sendText(messageEntityEncode.encode(messageVO));
            } catch (Exception e) {
                log.error("---------------WebSocket单点消息发送异常---------------");
            }
        }
    }

    /**
     * 发送多人单点消息
     * @param userIds
     * @param message
     */
    public void sendMoreMessage(Integer[] userIds, String message) {
        for(Integer userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error("---------------WebSocket多人单点消息发送异常---------------");
                }
            }
        }
    }
}
