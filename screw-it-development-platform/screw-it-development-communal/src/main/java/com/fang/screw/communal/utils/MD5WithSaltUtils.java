package com.fang.screw.communal.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @FileName MD5WithSaltUtils
 * @Description MD5加密
 * @Author yaoHui
 * @date 2024-07-27
 **/
public class MD5WithSaltUtils {

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /***
    * @Description 加密
    * @param input
    * @param salt
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    public static String md5WithSalt(String input,String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return toHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /***
    * @Description 验证
    * @param input
    * @param salt
    * @param encrypted
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    public static boolean verify(String input, String salt,String encrypted) {
        // 使用相同的盐值和输入数据加密，然后比较结果
        String encryptedInput = md5WithSalt(input,salt);

        return encryptedInput.equals(encrypted);
    }


}