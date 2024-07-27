package com.fang.screw.upm.enums;

import com.fang.screw.communal.utils.BusinessException;
import com.fang.screw.communal.utils.R;
import lombok.Getter;

@Getter
public enum ExceptionEnum {

    // 用户管理模块
    DUPLICATE_SYSOP_USER_NAME(50001, "用户名称重复"),
    DUPLICATE_SYSOP_USER_PHONE(50002, "手机号重复"),
    DUPLICATE_SYSOP_USER_EMAIL(50003, "邮箱重复"),
    DUPLICATE_SYSOP_ROLE_NAME(50005, "角色名称重复"),
    SYSOP_ROLE_NOT_EMPTY(50006, "角色下仍有用户，无法删除该角色"),
    DUPLICATE_SYSOP_APP_NAME(50007, "应用名称重复"),
    DUPLICATE_SYSOP_DS_NAME(50008, "数据源配置名称重复"),
    DUPLICATE_SYSOP_DATA_ACTION_NAME(50009, "数据存储目标配置名称重复"),
    SYSOP_ACTION_FAIL(50010, "无法执行本次操作，请稍后再试"),
    SYSOP_USERNAME_ERROR(50011, "账号或密码错误，请检测后重试"),
    ;

    private Integer code;
    private String desc;
    private final BusinessException exception;

    ExceptionEnum(Integer code, String msg) {
        exception = new BusinessException(code, msg);
    }

    public R<String> getBusinessException() {
        return exception.getDesc();
    }

}
