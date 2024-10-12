package com.fang.screw.client.process;

import com.fang.screw.client.annotation.HuiListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @FileName HuiListenerAnnotationBeanPostProcessor
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Component
public class HuiListenerAnnotationBeanPostProcessor implements BeanPostProcessor, InitializingBean {

    private static final HuiListenerRegistry huiListenerRegistry = new HuiListenerRegistry();

    public static boolean huiListenerFlag = false;

    /***
    * @Description 在 bean 的初始化方法（如 @PostConstruct 注解的方法或 init-method 指定的方法）之前调用。
    * @param bean
    * @param beanName
    * @return {@link Object }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /***
     * @Description 在 bean 的初始化方法之后调用。查看当前的bean是否存在被HuiListener注解过的方法
     * @param bean
     * @param beanName
     * @return {@link Object }
     * @Author yaoHui
     * @Date 2024/10/12
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Method[] methods = bean.getClass().getMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(HuiListener.class)){
                processHuiListener(method,bean);
                huiListenerFlag = true;
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void processHuiListener(Method method,Object bean){
        HuiListener huiListener = method.getAnnotation(HuiListener.class);
        HuiListenerEndpoint huiListenerEndpoint = new HuiListenerEndpoint();
        huiListenerEndpoint.setBean(bean);
        huiListenerEndpoint.setMethod(method);
        huiListenerRegistry.registerListenerEndpoint(huiListener.queueName(),huiListenerEndpoint);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
