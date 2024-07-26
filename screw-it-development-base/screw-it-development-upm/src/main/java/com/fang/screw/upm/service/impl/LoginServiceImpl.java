package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.utils.JWTUtils;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.upm.mapper.UserInfoMapper;
import com.fang.screw.upm.service.LoginService;
import com.fang.screw.upm.utils.R;
import com.fang.screw.upm.config.DynamicConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import po.UserInfoPO;
import vo.LoginVO;

import java.util.UUID;

/**
 * @FileName LoginServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private UserInfoMapper userInfoMapper;

    private RedisUtils redisUtils;

    @Override
    public R<String> signIn(LoginVO loginVO) {
        UserInfoPO userInfoPO = userInfoMapper.selectOne(Wrappers.<UserInfoPO>lambdaQuery()
                .eq(UserInfoPO::getUserName,loginVO.getUserName())
                .eq(UserInfoPO::getPassword,loginVO.getPassword()));

        if(ObjectUtils.isEmpty(userInfoPO)){
            return R.failed(DynamicConstant.LOGIN_ERROR);
        }

        String uuid = String.valueOf(UUID.randomUUID());

        redisUtils.set(uuid,userInfoPO);

        String token = JWTUtils.generateToken(uuid,userInfoPO.getRoleId());
        return R.ok(token);
    }
}