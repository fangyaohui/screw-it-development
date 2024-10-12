package com.fang.screw.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName HuiListener
 * @Description HuiMQ监听注解
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HuiListener {
    String queueName();
}
