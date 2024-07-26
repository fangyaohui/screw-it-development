package com.fang.screw.gateway.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description ScAuthenticationEntryPoint 认证失败执行
 * @since 2024/7/17 22:11
 */
@Component
@Slf4j
public class ScAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "000000");
        map.put("message", "未授权禁止访问");

        log.error("access forbidden path={}", exchange.getRequest().getPath());

        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}