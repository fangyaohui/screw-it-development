package com.fang.demo.comfangdemogateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description AuthenticationFilter
 * @since 2024/7/17 20:39
 */
@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().pathWithinApplication().value();

        log.info(path);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}