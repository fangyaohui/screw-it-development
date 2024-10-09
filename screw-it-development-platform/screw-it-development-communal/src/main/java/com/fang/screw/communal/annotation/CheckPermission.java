package com.fang.screw.communal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName CheckPermission
 * @Description 权限校验 限流 防刷 用于AOP
 * @Author yaoHui
 * @date 2024-10-09
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {

}
