package com.fang.screw.chat.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName RabbitMQConfig RabbitMQ 消息转换器
 * @Description
 * @Author yaoHui
 * @date 2024-10-31
 **/
@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter jackson2JsonMessageConverter){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
//        return rabbitTemplate;
//    }
}
