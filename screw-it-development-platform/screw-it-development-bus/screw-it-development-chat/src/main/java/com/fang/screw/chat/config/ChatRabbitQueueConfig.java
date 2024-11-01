package com.fang.screw.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName UpmRabbitQueueConfig
 * @Description
 * @Author yaoHui
 * @date 2024-11-01
 **/
@Configuration
public class ChatRabbitQueueConfig {

    @Bean
    public Queue updateUserInfoQueue(){
        return new Queue("updateUserInfoQueue",true);
    }

}
