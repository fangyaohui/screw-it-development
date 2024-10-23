package com.fang.screw.communal.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName CacheRemove
 * @Description
 * @Author yaoHui
 * @date 2024-10-23
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRemove {
    // Redis
    String value();
    String key();
    long expireTime();
}
