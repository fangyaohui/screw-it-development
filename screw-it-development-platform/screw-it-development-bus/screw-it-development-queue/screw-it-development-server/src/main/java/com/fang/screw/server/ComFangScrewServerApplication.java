package com.fang.screw.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
//@ComponentScan(value = {"com.fang.screw.communal.*","com.fang.screw.communal.config", "com.fang.screw.server.*"})
public class ComFangScrewServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewServerApplication.class, args);
    }

}
