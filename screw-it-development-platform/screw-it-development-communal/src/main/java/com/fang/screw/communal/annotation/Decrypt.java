package com.fang.screw.communal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName Decrypt
 * @Description 解密
 * @Author yaoHui
 * @date 2024-09-27
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {

    // 解密算法 0-AES密钥解密，1-RSA密钥解密
    int value() default 0;
}