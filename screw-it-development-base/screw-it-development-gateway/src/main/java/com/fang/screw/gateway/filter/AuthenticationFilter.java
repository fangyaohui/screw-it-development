package com.fang.screw.gateway.filter;

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

//        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            bearerToken =  bearerToken.substring(7);
//            String uuid = JWTUtils.getUUID(bearerToken);
//            CurrentUserHolder.setUser((BlogUserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN+uuid));
//        }

        log.info(path);
//        log.info(bearerToken);


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
