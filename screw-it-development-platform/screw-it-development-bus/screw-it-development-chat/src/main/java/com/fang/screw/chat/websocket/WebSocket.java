package com.fang.screw.chat.websocket;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.fang.screw.chat.config.CustomSpringConfigurator;
import com.fang.screw.chat.utils.MessageEntityDecode;
import com.fang.screw.chat.utils.MessageEntityEncode;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.domain.po.MessagePO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
@ServerEndpoint(
        value = "/websocket/{id}",
        decoders = { MessageEntityDecode.class },
        encoders = { MessageEntityEncode.class },
        configurator = CustomSpringConfigurator.class
)
@Slf4j
//@AllArgsConstructor
public class WebSocket {

    private Session session;
    private final Gson gson;
    private final RedisUtils redisUtils;
    private static final Map<Integer, Session> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    //这段代码是WebSocket端点的核心业务逻辑，包含了WebSocket生命周期的几个重要事件的处理函数，
    // 分别是连接打开（@OnOpen）、收到消息（@OnMessage）、连接关闭（@OnClose）以及发生错误（@OnError）

    @Autowired
    public WebSocket(Gson gson, RedisUtils redis) {
        this.gson = gson;
        this.redisUtils = redis;
    }

    @OnOpen
    public void onOpen(@PathParam("id") Integer id, Session session) {
        this.session = session;
        WEBSOCKET_MAP.put(id, session);
        log.info("WebSocket Open");
    }

    /***
    * @Description 处理客户端发送的消息，将消息持久化到Redis，并将消息发送给接收方（如果接收方在线）。
    * @param message
    * @return {@link  }
    * @Author yaoHui
    * @Date 2024/10/8
    */
    @OnMessage
    public void onMessage(MessagePO message) throws IOException {
        String key = LongStream.of(message.getSendUser(), message.getReceiveUser())
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("-"));
        redisUtils.set(key, message);
        if (WEBSOCKET_MAP.get(message.getReceiveUser()) != null) {
            WEBSOCKET_MAP.get(message.getReceiveUser()).getBasicRemote().sendText(gson.toJson(message));
        }
    }

    @OnClose
    public void onClose() {
        for (Map.Entry<Integer, Session> entry : WEBSOCKET_MAP.entrySet()) {
            if (this.session.getId().equals(entry.getValue().getId())) {
                WEBSOCKET_MAP.remove(entry.getKey());
                return;
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
