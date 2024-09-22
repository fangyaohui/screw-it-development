package com.fang.screw.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(value = {"com.fang.screw.communal.*", "com.fang.screw.blog.*"})
public class ComFangScrewBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewBlogApplication.class, args);
    }

}
