package com.fang.screw.gateway.config;

import com.fang.screw.gateway.filter.ScFilter;
//import com.fang.screw.gateway.filter.DecryptFilter;
import com.fang.screw.gateway.handler.ScAccessDeniedHandler;
import com.fang.screw.gateway.handler.ScAuthenticationEntryPoint;
import com.fang.screw.gateway.manager.ScAuthenticationManager;
import com.fang.screw.gateway.manager.ScAuthorizationManager;
import com.fang.screw.gateway.repository.ScSecurityContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author fangyaohui
 * @version 0.0.3
 * @description WebSecurityConfig SpringSecurity核心配置
 * @since 2024/7/17 22:29
 */
@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class WebSecurityConfig {
//
//    @Resource
//    PasswordEncoder passwordEncoder;

    ScSecurityContextRepository scSecurityContextRepository;

    ScAuthenticationManager scAuthenticationManager;

    ScAuthorizationManager scAuthorizationManager;

    ScAccessDeniedHandler scAccessDeniedHandler;

    ScAuthenticationEntryPoint scAuthenticationEntryPoint;

    /**
     * 访问权限授权
     *
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
//                .addFilterAt(new DecryptFilter(),SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(scSecurityContextRepository) //存储认证信息
                .authenticationManager(scAuthenticationManager) //认证管理
                .authorizeExchange(exchange -> exchange // 请求拦截处理
                        .pathMatchers("/favicon.ico","/api/upm/register/**","/api/upm/login/**","/front/**",
                                "/api/blog/resource/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().access(scAuthorizationManager) //权限
                )
                .addFilterAfter(new ScFilter(), SecurityWebFiltersOrder.AUTHORIZATION) //拦截处理
                .exceptionHandling().accessDeniedHandler(scAccessDeniedHandler) //权限认证失败
                .and()
                .exceptionHandling().authenticationEntryPoint(scAuthenticationEntryPoint); //认证失败

        return http.build();
    }
}
