package com.fang.screw.blog.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fang.screw.blog.service.SysConfigService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @FileName SysConfigController
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@RestController
@Slf4j
@RequestMapping("/sysConfig")
@AllArgsConstructor
public class SysConfigController {

    private SysConfigService sysConfigService;

    /***
    * @Description 查询系统参数
    * @return {@link R< Map< String, String>> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @GetMapping("/getListSysConfig")
    public R<Map<String, String>> getListSysConfig() {
        return sysConfigService.getListSysConfig();
    }

}
