package com.alibaba.nacos;

import com.alibaba.nacos.client.config.common.ConfigConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author yaoHui
 * @date 2024-07-24
 * 官方源码中：console 包中复制而来的主启动类
 * 源码运行便于开发
 */
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
public class AlibabaNacosApplication {

    public static void main(String[] args) {

        /**
         *设置单击模式启动
         */
        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
        /**
         * 是否开启认证
         */
        System.setProperty(ConfigConstants.AUTH_ENABLED, "false");

        SpringApplication.run(AlibabaNacosApplication.class, args);
    }
}
