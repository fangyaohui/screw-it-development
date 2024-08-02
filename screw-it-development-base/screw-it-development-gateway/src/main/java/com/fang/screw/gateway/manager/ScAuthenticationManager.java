package com.fang.screw.gateway.manager;

import com.fang.screw.communal.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import po.UserInfoPO;
import reactor.core.publisher.Mono;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description ScAuthenticationManager 从AuthenticationToken中读取Token并做用户数据分析
 * @since 2024/7/17 22:14
 */
@Slf4j
@Component
public class ScAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String tokenString = (String) authentication.getPrincipal();
        String token = getJwtToken(tokenString);

        // 校验Token是否合法
        Long roleId = null;
        if(JWTUtils.validateToken(token)){
            roleId = JWTUtils.getRoleId(token);
        }
        log.info("ScAuthenticationManager roleId = {}", roleId);
        Long finalRoleId = roleId;
        return Mono.just(authentication).map(auth -> {
            return new UsernamePasswordAuthenticationToken(finalRoleId, null, null);
        });
    }

    /**
     * 校验token
     *
     * @param tokenString
     * @return
     */
    private UserInfoPO parseToken(String tokenString) {
        //读取token
        String jwtToken = getJwtToken(tokenString);
        log.info("ScAuthenticationManager jwtToken = {}", jwtToken);

        //模拟认证成功
        if (StringUtils.hasText(jwtToken) && jwtToken.startsWith("a")) {
            UserInfoPO userInfoPO = new UserInfoPO();
            userInfoPO.setId(2L);
            userInfoPO.setUserName("fangyaohui");
            userInfoPO.setPassword("123456");
            userInfoPO.setRoleId(1L);
            return userInfoPO;
        }

        return null;
    }

    /**
     * 读取Jwt Token
     *
     * @param authorization
     * @return
     */
    private String getJwtToken(String authorization) {
        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        boolean valid = authorization.startsWith("Bearer ");
        if (!valid) {
            return null;
        }

        return authorization.replace("Bearer ", "");
    }


}
