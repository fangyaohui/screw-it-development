package com.fang.demo.comfangdemogateway.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import po.UserInfoPO;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description ScAuthorizationManager 权限验证 是否通过
 * @since 2024/7/17 22:23
 */

@Slf4j
@Component
public class ScAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {

        return authentication.map(auth -> {
            Long roleId = (Long) auth.getPrincipal();
            log.info("ScAuthorizationManager roleId = {}", roleId);

            if (Objects.isNull(roleId)) {
                return new AuthorizationDecision(false);
            }
            return new AuthorizationDecision(true);
        }).defaultIfEmpty(new AuthorizationDecision(false));

    }
}