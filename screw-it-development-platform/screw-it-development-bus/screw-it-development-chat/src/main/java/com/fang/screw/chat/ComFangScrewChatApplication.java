package com.fang.screw.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// 注意一旦使用了ComponentScan扫描后，需要在这里特别应用该微服务自己的ComponentScan
@ComponentScan(value = {"com.fang.screw.communal.*","com.fang.screw.communal.config", "com.fang.screw.chat.*","com.fang.screw.client.*"})
public class ComFangScrewChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewChatApplication.class, args);
    }

}
