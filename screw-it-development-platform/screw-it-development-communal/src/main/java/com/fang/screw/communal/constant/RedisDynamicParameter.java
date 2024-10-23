package com.fang.screw.communal.constant;

/**
 * @FileName RedisDynamicParameter
 * @Description
 * @Author yaoHui
 * @date 2024-07-28
 **/
public class RedisDynamicParameter {

    public static String REDIS_BASE_KEY = "screw:";

    // 用户登录Key
    public static String REDIS_USER_LOGIN_TOKEN = "screw:user:login:token:";

    public static String REDIS_USER_LOGIN_STATUS = "screw:user:login:status:";

    public static String REDIS_CRYPT_AES_PRIVATE = "screw:crypt:aes:private:";

}
