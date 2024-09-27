package com.fang.screw.communal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName Encrypt
 * @Description 加密
 * @Author yaoHui
 * @date 2024-09-27
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {

    // 加密算法 0-AES密钥加密，1-RSA公钥加密
    int value() default 0;
}
