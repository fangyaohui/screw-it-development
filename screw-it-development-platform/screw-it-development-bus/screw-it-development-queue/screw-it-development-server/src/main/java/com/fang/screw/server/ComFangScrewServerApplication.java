package com.fang.screw.upm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(value = {"com.fang.screw.communal.*","com.fang.screw.communal.config", "com.fang.screw.upm.*"})
public class ComFangScrewServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewUpmApplication.class, args);
    }

}
