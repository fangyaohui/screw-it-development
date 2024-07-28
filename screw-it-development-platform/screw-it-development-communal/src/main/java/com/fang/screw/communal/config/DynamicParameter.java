package com.fang.screw.communal.config;

/**
 * @FileName DynamicParameter
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
public class DynamicParameter {

    // jwt秘钥
    public static final String JWT_SECURITY = "security";

    // Token有效期10小时
    public static final Integer EXPIRATION_TIME = 1000*60*60*10;

    // 登录状态有效期9小时
    public static final Integer REDIS_LOGIN_STATUS_EXPIRATION_TIME = 1000*60*60*9;

}