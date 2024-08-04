package com.fang.screw.communal.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @FileName TimeUtils
 * @Description
 * @Author yaoHui
 * @date 2024-08-04
 **/
public class TimeUtils {

    public static String getCurrentTimeStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
