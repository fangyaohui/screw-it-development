package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.SysConfigMapper;
import com.fang.screw.blog.service.SysConfigService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.enums.PoetryEnum;
import com.fang.screw.domain.po.SysConfigPO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @FileName SysConfigServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@Service
@Slf4j
@AllArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigPO> implements SysConfigService {

    private SysConfigMapper sysConfigMapper;

    /***
     * @Description 查询系统参数
     * @return {@link R< Map< String, String>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<Map<String, String>> getListSysConfig() {

        Map<String,String> sysConfigPOMap = sysConfigMapper.selectList(Wrappers.<SysConfigPO>lambdaQuery()
                .eq(SysConfigPO::getConfigType,Integer.toString(PoetryEnum.SYS_CONFIG_PUBLIC.getCode())))
                .stream().collect(Collectors.toMap(SysConfigPO::getConfigKey,SysConfigPO::getConfigValue));

        return R.success(sysConfigPOMap);
    }
}
