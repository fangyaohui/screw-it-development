package com.fang.screw.client.process;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @FileName HuiListenerEndpoint
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Data
public class HuiListenerEndpoint {

    private Method method;

    private Object bean;
}
