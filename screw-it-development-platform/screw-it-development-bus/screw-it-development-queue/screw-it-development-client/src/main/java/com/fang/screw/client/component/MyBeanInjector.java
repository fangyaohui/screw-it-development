package com.fang.screw.client.component;

import com.fang.screw.client.Thread.GetMessageThread;
import com.fang.screw.client.process.HuiListenerAnnotationBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @FileName MyBeanInjector
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Component
@Slf4j
public class MyBeanInjector implements SmartInitializingSingleton {

    private final NettyClient nettyClient;

    public MyBeanInjector(NettyClient nettyClient){
        this.nettyClient = nettyClient;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("GetMessageThread is ready");
        if (HuiListenerAnnotationBeanPostProcessor.huiListenerFlag){
            GetMessageThread getMessageThread = new GetMessageThread(nettyClient);
            log.info("GetMessageThread is running");
        }
    }
}
