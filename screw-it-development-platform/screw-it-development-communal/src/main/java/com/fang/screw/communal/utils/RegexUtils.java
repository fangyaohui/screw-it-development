package com.fang.screw.communal.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @FileName RegexUtils
 * @Description
 * @Author yaoHui
 * @date 2024-07-27
 **/
public class RegexUtils {

    /***
    * @Description 手机号验证
    * @param str
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    public static boolean isMobile(String str)
    {
        if(ObjectUtils.isEmpty(str)){
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
    * @Description 判断该邮件地址是否合法
    * @param address
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    public static boolean isEmailAddress(String address) {
        // 是否合法
        boolean flag = false;
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        try {
            String[] addressArr = address.split(",");
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = null;
            for (String str : addressArr) {
                matcher = regex.matcher(str);
                flag = matcher.matches();
                if (!flag) {
                    return false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}