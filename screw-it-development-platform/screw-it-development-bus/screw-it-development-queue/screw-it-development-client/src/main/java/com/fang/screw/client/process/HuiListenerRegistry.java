package com.fang.screw.client.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.ConcurrentHashSet;
import com.fang.screw.client.component.NettyClient;
import com.fang.screw.client.protocol.MessageBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName HuiListenerRegistry
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Slf4j
public class HuiListenerRegistry {

    public static final ConcurrentHashMap<String,HuiListenerEndpoint> huiListenerEndpointConcurrentHashMap = new ConcurrentHashMap<>();

    /***
    * @Description 添加HuiListener监听的方法
    * @param queueName
    * @param huiListenerEndpoint
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    public void registerListenerEndpoint(String queueName,HuiListenerEndpoint huiListenerEndpoint){
        huiListenerEndpointConcurrentHashMap.put(queueName,huiListenerEndpoint);
    }

    /***
    * @Description 消费者处理收到消息的主要逻辑
    * @param message
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    public boolean handleMessage(MessageBase.Message message){
        HuiListenerEndpoint huiListenerEndpoint = huiListenerEndpointConcurrentHashMap.get(message.getChannel());
        if(ObjectUtils.isEmpty(huiListenerEndpoint)){
            log.info("消息无对应Channel消费：" + message.getChannel());
            return true;
        }

        Method method = huiListenerEndpoint.getMethod();
        Object bean = huiListenerEndpoint.getBean();

        try{
            Class<?>[] classes = method.getParameterTypes();
            method.invoke(bean,JSON.parseObject(message.getContent(),classes[0]));
        }catch (Exception e){
            log.info("消息消费异常");
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
