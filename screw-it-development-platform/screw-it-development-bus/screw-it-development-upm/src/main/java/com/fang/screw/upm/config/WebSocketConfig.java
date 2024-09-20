package com.fang.screw.upm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @FileName WebSocketConfig
 * @Description WebSocket配置类 用于配置WebSocket消息代理和注册STOMP端点
 * @Author yaoHui
 * @date 2024-09-20
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置简单消息代理，用于处理前缀为 /topic 的消息
        config.enableSimpleBroker("/topic");
        // 配置应用程序目的地前缀，发送消息时应以 /app 开头
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 WebSocket 端点，客户端会通过该端点连接到服务器
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 添加允许的来源，使用 allowedOriginPatterns 代替 allowedOrigins
        config.addAllowedOriginPattern("http://localhost:*");  // 允许所有端口的 localhost
        config.setAllowCredentials(true); // 允许发送Cookie或认证信息
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
