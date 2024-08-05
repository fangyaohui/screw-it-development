package com.fang.screw.communal.config;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @FileName RedisConfig
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
@Configuration
public class RedisConfig {

    @Resource
    RedisConnectionFactory connectionFactory;

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){

        RedisTemplate<String,Object> template = new RedisTemplate<>();
//        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(buildRedisSerializer());

        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(buildRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 构建 Redis 序列化器
     */
    private static RedisSerializer<?> buildRedisSerializer() {
        RedisSerializer<Object> json = RedisSerializer.json();
        // 解决 LocalDateTime 的序列化
        ObjectMapper objectMapper = (ObjectMapper) ReflectUtil.getFieldValue(json, "mapper");
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeToTimestampSerializer());
        simpleModule.addDeserializer(LocalDateTime.class, new TimestampToLocalDateTimeDeserializer());
        objectMapper.registerModule(simpleModule);
        // 解决其他时间序列化
        objectMapper.registerModules(new JavaTimeModule());
        return json;
    }
}
