package com.fang.screw.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
//@EnableWebFluxSecurity
public class ComFangScrewGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComFangScrewGatewayApplication.class, args);
    }
}
