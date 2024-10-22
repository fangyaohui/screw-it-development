package com.fang.screw.upm.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @FileName MyBeanPostProcessor
 * @Description
 * @Author yaoHui
 * @date 2024-10-13
 **/
@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor, InitializingBean {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.error("初始化之前执行：" + bean.toString());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.error("初始化之后执行：" + bean.toString());
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("afterPropertiesSet is running");
    }
}
