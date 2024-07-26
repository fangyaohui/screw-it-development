package com.fang.screw.gateway.repository;

import com.fang.screw.gateway.manager.ScAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description ScSecurityContextRepository 把header拿到的token放入AuthenticationToken
 * @since 2024/7/17 22:31
 */
@Slf4j
@Component
@AllArgsConstructor
public class ScSecurityContextRepository implements ServerSecurityContextRepository {

    private ScAuthenticationManager scAuthenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst("Authorization");
        log.info("ScSecurityContextRepository authorization = {}", authorization);

        return scAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authorization, null))
                .map(SecurityContextImpl::new);
    }
}