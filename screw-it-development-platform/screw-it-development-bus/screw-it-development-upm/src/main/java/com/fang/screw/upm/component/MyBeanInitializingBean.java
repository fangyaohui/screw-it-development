package com.fang.screw.upm.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @FileName MyBeanInitializingBean
 * @Description
 * @Author yaoHui
 * @date 2024-10-13
 **/
@Component
@Slf4j
public class MyBeanInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("afterPropertiesSet is running");
    }
}
