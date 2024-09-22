package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;

import java.util.Map;

/**
 * @FileName SysConfigService
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
public interface SysConfigService {

    /***
     * @Description 查询系统参数
     * @return {@link R< Map< String, String>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<Map<String, String>> getListSysConfig();
}
