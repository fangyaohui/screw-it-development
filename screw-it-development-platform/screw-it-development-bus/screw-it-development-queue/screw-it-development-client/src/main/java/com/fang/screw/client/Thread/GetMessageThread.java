package com.fang.screw.client.Thread;

import com.fang.screw.client.component.NettyClient;
import com.fang.screw.client.process.HuiListenerRegistry;
import com.fang.screw.client.protocol.MessageBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @FileName getMessageThread
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Slf4j
public class GetMessageThread {

    private NettyClient nettyClient;

//    private static HuiListenerRegistry huiListenerRegistry = new HuiListenerRegistry();

    public GetMessageThread(NettyClient nettyClient){
        this.nettyClient = nettyClient;
        regularGetMessage();
    }

    /***
    * @Description 定时发送是否存在消息
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    private void regularGetMessage(){
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> {
//            log.info("regularGetMessage is running");
            Set<String> channel = HuiListenerRegistry.huiListenerEndpointConcurrentHashMap.keySet();
            for(String s : channel){
                MessageBase.Message message = MessageBase.Message.newBuilder()
                        .setCmd(MessageBase.Message.CommandType.SEND_MESSAGE)
                        .setChannel(s).build();
                nettyClient.sendMessage(message);
            }

        };

        scheduledExecutorService.scheduleAtFixedRate(task,2,3, TimeUnit.SECONDS);
    }


}
