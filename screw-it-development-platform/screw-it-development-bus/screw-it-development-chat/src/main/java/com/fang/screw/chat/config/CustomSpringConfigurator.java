package com.fang.screw.chat.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @FileName CustomSpringConfigurator
 * @Description 这段代码的主要作用是将Spring框架与WebSocket集成，使得WebSocket端点能够通过Spring的依赖注入机制获取Spring管理的Bean。
 * 具体来说，它通过实现ServerEndpointConfig.Configurator接口和ApplicationContextAware接口，将Spring的ApplicationContext
 * 注入到WebSocket配置中，从而允许WebSocket端点使用Spring的依赖注入功能。
 * @Author yaoHui
 * @date 2024-10-08
 **/
public class CustomSpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static volatile BeanFactory context;

    @Override
    public  <T> T getEndpointInstance(Class<T> clazz) {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomSpringConfigurator.context = applicationContext;
    }

}
