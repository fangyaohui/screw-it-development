//package com.fang.screw.upm.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @FileName WebConfig
// * @Description
// * @Author yaoHui
// * @date 2024-09-21
// **/
//@Configuration
//public class WebCorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:13628")  // 或者指定前端的域名
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .allowedHeaders("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With")
//        ;
//    }
//}
