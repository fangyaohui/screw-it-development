package com.fang.screw.blog;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.fang.screw.communal.*", "com.fang.screw.blog.*"})
public class ComFangScrewBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewBlogApplication.class, args);
    }

}
