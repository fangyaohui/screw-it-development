package com.fang.screw.upm;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.fang.screw.communal.*","com.fang.screw.communal.config", "com.fang.screw.upm.*"})
public class ComFangScrewUpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewUpmApplication.class, args);
    }

}
