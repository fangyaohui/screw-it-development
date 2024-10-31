package com.fang.screw.blog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName SimpleQueueConfig
 * @Description 定义简单队列服务
 * @Author yaoHui
 * @date 2024-10-31
 **/
@Configuration
public class SimpleQueueConfig {

    /***
    * @Description 定义一个上传图片任务的消息队列
    * @return {@link Queue }
    * @Author yaoHui
    * @Date 2024/10/31
    */
    @Bean
    public Queue UploadImageSimpleQueue(){
        return new Queue("uploadImageSimpleQueue",true);
    }

    /***
    * @Description 申请一个Direct交换机——该交换机会将所有符合路由的消息转发到该交换机下绑定的队列
    * @return {@link DirectExchange }
    * @Author yaoHui
    * @Date 2024/10/31
    */
    @Bean
    public DirectExchange UploadImageExchange(){
        return new DirectExchange("uploadImageExchange",true,false);
    }

    /***
    * @Description 绑定上传图片的消息队列到指定的交换机中
    * @return {@link Binding }
    * @Author yaoHui
    * @Date 2024/10/31
    */
    @Bean
    public Binding bindingUploadImageQueueExchange(){
        return BindingBuilder.bind(UploadImageSimpleQueue()).to(UploadImageExchange()).with("uploadImageKey");
    }
}
