package com.fang.demo.comfangdemoupm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.demo.comfangdemocommunal.service.UserService;
import com.fang.demo.comfangdemoupm.mapper.UserInfoMapper;

import org.apache.dubbo.config.annotation.DubboService;
import po.UserInfoPO;

import java.util.List;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@DubboService
//@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoPO> implements UserService {


    @Override
    public List<UserInfoPO> getAllUserInfoList() {
        return baseMapper.selectList(null);
    }
}