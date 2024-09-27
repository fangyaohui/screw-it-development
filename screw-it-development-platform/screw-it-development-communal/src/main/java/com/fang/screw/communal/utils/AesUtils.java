package com.fang.screw.communal.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @FileName AesUtils
 * @Description
 * @Author yaoHui
 * @date 2024-09-27
 **/
public class AesUtils {

    /**
     * 加密算法AES
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * key的长度，Wrong key size: must be equal to 128, 192 or 256
     * 传入时需要16、24、36
     */
    private static final Integer KEY_LENGTH = 16 * 8;

    /**
     * 算法名称/加密模式/数据填充方式
     * 默认：AES/ECB/PKCS5Padding
     */
    private static final String ALGORITHMS = "AES/CBC/PKCS5Padding";

    private static final BouncyCastleProvider PROVIDER = new BouncyCastleProvider();

    /**
     * 后端AES的key，由静态代码块赋值
     */
    public static String key;

    static {
        key = getKey();
    }

    /**
     * 获取key
     */
    public static String getKey() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < KEY_LENGTH / 8; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }

    /***
    * @Description
    * @param base64Ciphertext
    * @param base64Iv
    * @param secretKey
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/9/27
    */
    public static String decrypt(String base64Ciphertext, String base64Iv, String secretKey) throws Exception {
        // 将secretKey转换为字节数组（与前端的UTF-8解析保持一致）
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        // 生成AES的SecretKeySpec
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

        // 解码前端传来的Base64格式的IV和ciphertext
        byte[] ivBytes = Base64.decodeBase64(base64Iv);
        byte[] cipherTextBytes = Base64.decodeBase64(base64Ciphertext);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Java中PKCS5Padding与PKCS7Padding兼容
        if(ObjectUtils.isEmpty(ivBytes)){
            System.out.println("fang"+base64Iv);
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // 解密
        byte[] decryptedBytes = cipher.doFinal(cipherTextBytes);

        // 将解密后的字节数组转换为字符串并返回
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS,PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM));

        //调用doFinal
        byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

        // 转base64
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        //base64格式的key字符串转byte
        byte[] decodeBase64 = Base64.decodeBase64(encryptStr);

        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS,PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), KEY_ALGORITHM));

        //调用doFinal解密
        byte[] decryptBytes = cipher.doFinal(decodeBase64);
        return new String(decryptBytes);
    }

}
