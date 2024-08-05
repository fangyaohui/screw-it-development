package com.fang.screw.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName PermissionCategoryEnum
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@AllArgsConstructor
@Getter
public enum PermissionCategoryEnum {

    READ(1, "Read"),
    WRITE(2, "Write"),
    READ_WRITE(3, "Read/Write"),
    OTHER(4, "Other");

    private final Integer code;
    private final String desc;

    public static List<Map<String, String>> getAllAsMap() {
        List<Map<String,String>> mapList = new ArrayList<>();
        for (PermissionCategoryEnum constant : PermissionCategoryEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("dsType", constant.getCode().toString());
            map.put("dsTypeName", constant.getDesc());
            mapList.add(map);
        }
        return mapList;
    }

    public static String getDescByCode(Integer code) {
        for (PermissionCategoryEnum category : PermissionCategoryEnum.values()) {
            if (category.getCode().equals(code)) {
                return category.getDesc();
            }
        }
        // 如果没有找到对应的code，可以抛出一个异常或者返回一个默认值
        throw new IllegalArgumentException("No PermissionCategoryEnum found for code: " + code);
    }

    public static Integer getCodeByDesc(String desc) {
        for (PermissionCategoryEnum enumItem : PermissionCategoryEnum.values()) {
            if (enumItem.getDesc().equals(desc)) {
                return enumItem.getCode();
            }
        }
        // 如果没有找到对应的desc，可以抛出一个异常或者返回一个默认值
        throw new IllegalArgumentException("No PermissionCategoryEnum found for desc: " + desc);
    }

}