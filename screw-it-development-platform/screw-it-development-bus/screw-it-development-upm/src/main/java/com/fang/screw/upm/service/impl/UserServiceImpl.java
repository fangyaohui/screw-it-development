package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.service.UserService;
import com.fang.screw.upm.mapper.UserInfoMapper;

import org.springframework.stereotype.Service;
import po.UserInfoPO;

import java.util.List;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
//@DubboService
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoPO> implements UserService {


    @Override
    public List<UserInfoPO> getAllUserInfoList() {
        return baseMapper.selectList(null);
    }
}