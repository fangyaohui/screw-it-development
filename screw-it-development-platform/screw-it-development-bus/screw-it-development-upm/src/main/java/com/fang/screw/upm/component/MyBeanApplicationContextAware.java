package com.fang.screw.upm.component;

import com.fang.screw.upm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @FileName MyBeanApplicationContextAware
 * @Description
 * @Author yaoHui
 * @date 2024-10-13
 **/
@Slf4j
@Component
public class MyBeanApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.error("ApplicationContextAware is running");
    }

    public void displayUserServiceBean(){
        UserService userService = applicationContext.getBean(UserService.class);
        userService.testApplicationContextAware();
    }
}
