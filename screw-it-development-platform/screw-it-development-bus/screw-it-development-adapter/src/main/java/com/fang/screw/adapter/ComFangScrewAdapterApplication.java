package com.fang.screw.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.fang.screw.adapter.*"})
public class ComFangScrewAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewAdapterApplication.class, args);
    }
}
