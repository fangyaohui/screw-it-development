package com.fang.screw.communal.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName CacheRemove
 * @Description
 * @Cacheable(value = "page:article",key = "#0.current:#0.keyWord") 表示对象中的某个属性0表示第一个
 * @Cacheable(value = "article:getArticleById",key = "#id") 表示方法参数，
 * @Author yaoHui
 * @date 2024-10-23
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRemove {
    String value() default "";
    String key() default "";
    long expireTime() default 10*60;
}
