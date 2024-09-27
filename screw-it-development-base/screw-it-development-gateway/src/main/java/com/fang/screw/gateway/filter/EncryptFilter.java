//package com.fang.screw.gateway.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author fangyaohui
// * @version 0.0.3
// * @description EncryptFilter 对所有响应进行加密
// * @since 2024/7/17 20:39
// */
//@Component
//@Slf4j
//public class EncryptFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 获取响应
//        ServerHttpResponse response = exchange.getResponse();
//        log.info("响应");
//
//        // 在响应完成后执行
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            // 修改响应状态
//            if (response.getStatusCode() == HttpStatus.OK) {
//                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//
//                // 这里可以修改响应内容
//                // 例如，可以在响应中添加自定义信息
//                String newBody = "{\"message\":\"Custom Response Message\"}";
//                response.getHeaders().setContentLength(newBody.length());
//                response.writeWith(Mono.just(response.bufferFactory().wrap(newBody.getBytes(StandardCharsets.UTF_8))));
//            }
//        }));
//    }
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//}
