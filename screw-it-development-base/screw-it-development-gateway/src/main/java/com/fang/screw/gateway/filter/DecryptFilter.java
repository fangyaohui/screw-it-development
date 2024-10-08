//package com.fang.screw.gateway.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.fang.screw.communal.utils.AesUtils;
//import com.fang.screw.communal.utils.R;
//import com.fang.screw.communal.utils.RedisUtils;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.support.BodyInserterContext;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DefaultDataBufferFactory;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_CRYPT_AES_PRIVATE;
//
///**
// * @author fangyaohui
// * @version 0.0.3
// * @description EncryptAndDecryptFilter 对所有的请求进行解密
// * @since 2024/7/17 22:25
// */
//@Slf4j
////@Component
////@AllArgsConstructor
//public class DecryptFilter implements WebFilter {
//
////    @Autowired
////    private ReactiveRedisTemplate<String, String> redisTemplate;
//
//
//    @NotNull
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        // 检查是否是POST请求和Content-Type为application/json
//        if (Objects.equals(request.getMethod(), HttpMethod.POST) && MediaType.APPLICATION_JSON.isCompatibleWith(request.getHeaders().getContentType())) {
//            return DataBufferUtils.join(request.getBody())
//                    .flatMap(dataBuffer -> {
//                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(bytes);
//                        DataBufferUtils.release(dataBuffer);
//
//                        String body = new String(bytes, StandardCharsets.UTF_8);
//
//                        // 假设body是一个JSON字符串，这里简化为Map处理
//                        // 这里需要JSON解析，实际中可以使用Jackson或Gson
//                        // 这里只是示例，假设已经解析完毕
//                        Map<String, Object> map = JSON.parseObject(body, Map.class);
//
//                        // 假设只有一个需要解密的参数"encryptedParam"
//                        try {
//
//                            // 创建修改后的请求
//                            String finalDecryptedParam = AesUtils.decrypt((String) map.get("encryptedData"),
//                                    (String) map.get("iv"),"Sio0J4c922So32PH");
//
//                            byte[] decryptedBytes = finalDecryptedParam.getBytes(StandardCharsets.UTF_8);
//                            long contentLength = decryptedBytes.length;
//                            DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
//                            DataBuffer buffer = bufferFactory.wrap(decryptedBytes);
//                            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
//                                @Override
//                                public Flux<DataBuffer> getBody() {
//                                    return Flux.just(buffer);
//                                }
//
//                                // 注意：这里不能直接修改headers，但可以通过其他方式传递信息
//                                // 一种方法是使用ServerWebExchange的attributes来存储Content-Length
//                                @Override
//                                public HttpHeaders getHeaders() {
//                                    HttpHeaders headers = new HttpHeaders();
//                                    headers.addAll(super.getHeaders());
//                                    // 注意：这里不能直接设置，因为HttpHeaders也是不可变的
//                                    // 但我们可以在后续处理中读取这个值
//                                    exchange.getAttributes().put("decryptedContentLength", contentLength);
//                                    return headers; // 返回原始headers，因为我们不能直接修改它
//                                }
//                            };
//
//                            // 传递修改后的请求
//                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        return chain.filter(exchange);
//                    });
//        }
//
//        // 如果不是POST或不是JSON，直接传递请求
//        return chain.filter(exchange);
//    }
//
//
//
//
////    @NotNull
////    @Override
////    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
////
////        ServerHttpRequest request = exchange.getRequest();
////
////
////        String url = request.getURI().getPath();
////        if (StringUtils.contains(url, "login")) {
////            return chain.filter(exchange);
////        }
////
////        try {
//////            RedisUtils redisUtils = new RedisUtils();
////            String token = request.getHeaders().getFirst("Authorization");
////
////            String privateLey = String.valueOf(redisTemplate.opsForValue().get(REDIS_CRYPT_AES_PRIVATE+token));
////
////
////            // 获取解密后的URL
//////            url = AesUtils.decrypt(url,privateLey);
////
////            // 需要对URL进行解密后处理
////            if (request.getMethod() != HttpMethod.POST && request.getMethod() != HttpMethod.PUT && request.getMethod() != HttpMethod.PATCH) {
////                // 如果不是post（新增）、put（全量修改）、patch（部分字段修改）操作，则直接放行
////                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
////                        .path(url)
////                        .build();
////                return chain.filter(exchange.mutate().request(modifiedRequest).build());
////            }
////
////            String encryptedBody = request.getBody().toString();
////
////            return chain.filter(exchange);
////
//////            String decryptedBody = AesUtils.decrypt(encryptedBody,privateLey);
////
////
////        }catch (Exception e){
////            e.printStackTrace();
////            return chain.filter(exchange);
////        }
////    }
//}
