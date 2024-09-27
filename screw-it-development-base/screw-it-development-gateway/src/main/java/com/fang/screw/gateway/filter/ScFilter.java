package com.fang.screw.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description ScFilter 请求通过后的额外操作处理
 * @since 2024/7/17 22:25
 */
@Slf4j
public class ScFilter implements WebFilter {

    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        log.info("UserFilter doing.... path={}", exchange.getRequest().getPath());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.isNull(authentication)){
            Object principal = authentication.getPrincipal();
            log.info("UserFilter doing principal={}", principal);
        }
        return chain.filter(exchange);
    }
}
