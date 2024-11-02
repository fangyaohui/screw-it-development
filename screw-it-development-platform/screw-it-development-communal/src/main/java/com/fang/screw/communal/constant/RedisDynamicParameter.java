package com.fang.screw.communal.constant;

/**
 * @FileName RedisDynamicParameter
 * @Description
 * @Author yaoHui
 * @date 2024-07-28
 **/
public class RedisDynamicParameter {

    public static String REDIS_BASE_KEY = "screw:";

    // 用户登录Key 保存用户userInfo
    public static String REDIS_USER_LOGIN_TOKEN = "screw:user:login:token:";

    // 用户token 通过上面Key 然后对这个key进行JWT解密得到UUID 通过UUID获取用户对应用户信息
    public static String REDIS_USER_LOGIN_STATUS = "screw:user:login:status:";

    public static String REDIS_CRYPT_AES_PRIVATE = "screw:crypt:aes:private:";

}
