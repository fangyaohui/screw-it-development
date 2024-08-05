package com.fang.screw.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName PermissionScopeEnum
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@AllArgsConstructor
@Getter
public enum PermissionScopeEnum {

    COMMON_BLOG(1, "普通博客"),
    COMMON_VIDEO(2, "普通视频"),
    VIP_BLOG(3, "VIP博客"),
    VIP_VIDEO(4, "VIP视频"),
    PAID_BLOG(5, "付费博客"),
    PAID_VIDEO(6, "付费视频"),
    ALL_RESOURCES(7, "所有资源");

    private final Integer code;
    private final String desc;

    public static List<Map<String, String>> getAllAsMap() {
        List<Map<String,String>> mapList = new ArrayList<>();
        for (PermissionScopeEnum constant : PermissionScopeEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("dsType", constant.getCode().toString());
            map.put("dsTypeName", constant.getDesc());
            mapList.add(map);
        }
        return mapList;
    }

    public static String getDescByCode(Integer code) {
        for (PermissionScopeEnum category : PermissionScopeEnum.values()) {
            if (category.getCode().equals(code)) {
                return category.getDesc();
            }
        }
        // 如果没有找到对应的code，可以抛出一个异常或者返回一个默认值
        throw new IllegalArgumentException("No PermissionScopeEnum found for code: " + code);
    }

    public static Integer getCodeByDesc(String desc) {
        for (PermissionScopeEnum enumItem : PermissionScopeEnum.values()) {
            if (enumItem.getDesc().equals(desc)) {
                return enumItem.getCode();
            }
        }
        // 如果没有找到对应的desc，可以抛出一个异常或者返回一个默认值
        throw new IllegalArgumentException("No PermissionScopeEnum found for desc: " + desc);
    }
}