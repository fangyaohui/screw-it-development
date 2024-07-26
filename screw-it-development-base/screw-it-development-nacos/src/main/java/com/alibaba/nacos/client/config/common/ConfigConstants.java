package com.alibaba.nacos.client.config.common;

/**
 * Nacos 属性自定义覆盖nacos 默认配置
 * 包名与阿里巴巴官方保持一致（com.alibaba.nacos.client.config.common）
 *
 * @author Alay
 * @date 2021-08-08 00:53
 */
public class ConfigConstants {
    /**
     * 单击模式启动
     */
    public static final String STANDALONE_MODE = "nacos.standalone";

    /**
     * 是否开启认证
     */
    public static final String AUTH_ENABLED = "nacos.core.auth.enabled";


    /**
     * 以下是 Nacos 官方源码常量
     */
    public static final String TENANT = "tenant";
    public static final String DATA_ID = "dataId";
    public static final String GROUP = "group";
    public static final String CONTENT = "content";
    public static final String CONFIG_TYPE = "configType";
    public static final String ENCRYPTED_DATA_KEY = "encryptedDataKey";
    public static final String TYPE = "type";
}
