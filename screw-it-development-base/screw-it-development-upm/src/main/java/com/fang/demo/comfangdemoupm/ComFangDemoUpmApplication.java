package com.fang.demo.comfangdemoupm;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.fang.demo.comfangdemocommunal.*", "com.fang.demo.comfangdemoupm.*"})
public class ComFangDemoUpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangDemoUpmApplication.class, args);
    }

}
