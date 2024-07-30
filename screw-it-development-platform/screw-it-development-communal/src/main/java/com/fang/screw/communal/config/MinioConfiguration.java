package com.fang.screw.communal.config;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @FileName MinioConfiguration
 * @Description Minio配置类
 * @Author yaoHui
 * @date 2024-07-30
 **/

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "minio")
public class MinioConfiguration {

    @Resource
    private MinioProperties ossProperties;

    @Bean
    @SneakyThrows
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(ossProperties.getEndpoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
    }


}
