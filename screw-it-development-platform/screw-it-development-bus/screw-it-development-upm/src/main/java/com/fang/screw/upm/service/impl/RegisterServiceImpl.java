package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.constant.RedisDynamicParameter;
import com.fang.screw.communal.utils.*;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.enums.ExceptionEnum;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.mapper.UserMapper;
import com.fang.screw.upm.service.RegisterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.RegisterUserInfoVO;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fang.screw.communal.constant.DynamicParameter.*;

/**
 * @FileName RegisterServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-27
 **/
@Service
@Slf4j
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private BlogUserMapper blogUserMapper;

    private UserMapper userMapper;

    private RedisUtils redisUtils;

    /***
     * @Description 注册用户
     * @param registerUserInfoVO
     * @return {@link R< Boolean> }
     * @Author yaoHui
     * @Date 2024/7/27
     */
    @Override
    public R<String> registerUser(RegisterUserInfoVO registerUserInfoVO){

        // 判断用户名是否重复
        if(blogUserMapper.exists(Wrappers.<BlogUserPO>lambdaQuery()
                .eq(StringUtils.isNotBlank(registerUserInfoVO.getUserName())
                        ,BlogUserPO::getUserName,
                        registerUserInfoVO.getUserName()))){
            return ExceptionEnum.DUPLICATE_SYSOP_USER_NAME.getBusinessException();
        }

        // 判断邮箱是否重复
        if(StringUtils.isNotBlank(registerUserInfoVO.getEmail()) && blogUserMapper.exists(Wrappers.<BlogUserPO>lambdaQuery()
                .eq(RegexUtils.isEmailAddress(registerUserInfoVO.getEmail())
                        ,BlogUserPO::getEmail,
                        registerUserInfoVO.getEmail()))){
            return ExceptionEnum.DUPLICATE_SYSOP_USER_EMAIL.getBusinessException();
        }

        // 判断手机号是否重复
        if(StringUtils.isNotBlank(registerUserInfoVO.getPhone()) && blogUserMapper.exists(Wrappers.<BlogUserPO>lambdaQuery()
                .eq(RegexUtils.isMobile(registerUserInfoVO.getPhone())
                        ,BlogUserPO::getPhone,
                        registerUserInfoVO.getPhone()))){
            return ExceptionEnum.DUPLICATE_SYSOP_USER_PHONE.getBusinessException();
        }

        BlogUserPO blogUserPO = registerUserInfoVO.transformToBlogUserPo();

        // 随机产生salt
        String salt = String.valueOf(UUID.randomUUID());
        blogUserPO.setSalt(salt);

        // 获取密文
        blogUserPO.setPassword(MD5WithSaltUtils.md5WithSalt(registerUserInfoVO.getPassword(),salt));
        blogUserPO.setLastLogin(LocalDateTime.now());
        blogUserPO.setNickName(blogUserPO.getUserName());

        log.info(blogUserPO.toString());
        if( blogUserMapper.insert(blogUserPO) > 0){
            return R.ok();
        }
        return ExceptionEnum.SYSOP_ACTION_FAIL.getBusinessException();
    }

    @Override
    public R<UserVO> registerUser(UserVO userVO) {

        // 判断用户名是否重复
        if(userMapper.exists(Wrappers.<UserPO>lambdaQuery()
                .eq(StringUtils.isNotBlank(userVO.getUsername())
                        ,UserPO::getUsername,
                        userVO.getUsername()))){
            return R.fail("用户名重复");
        }

        // 判断邮箱是否重复
        if(StringUtils.isNotBlank(userVO.getEmail()) && userMapper.exists(Wrappers.<UserPO>lambdaQuery()
                .eq(RegexUtils.isEmailAddress(userVO.getEmail())
                        ,UserPO::getEmail,
                        userVO.getEmail()))){
            return R.fail("邮箱重复");
        }

        // 判断手机号是否重复
        if(StringUtils.isNotBlank(userVO.getPhoneNumber()) && userMapper.exists(Wrappers.<UserPO>lambdaQuery()
                .eq(RegexUtils.isMobile(userVO.getPhoneNumber())
                        ,UserPO::getPhoneNumber,
                        userVO.getPhoneNumber()))){
            return R.fail("手机号重复");
        }

        UserPO userPO = userVO.transformPO();

        // 获取密文
        userPO.setPassword(MD5WithSaltUtils.md5WithSalt(userVO.getPassword(),SALT));
//        userPO.setCreateTime(LocalDateTime.now());
        userPO.setUserStatus(true);
        userPO.setUserType(2);

        // 保存
        userMapper.insert(userPO);

        userPO = userMapper.selectOne(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getUsername,userVO.getUsername()));

        String uuid = String.valueOf(UUID.randomUUID());

        // 判断该用户是否登录 登陆过就无需再生成Token然后保存到Redis中
        String token = (String) redisUtils.get(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userPO.getId());

        userVO = userPO.transformVO();
        userVO.setPassword(null);
        userVO.setAccessToken(token);

        if(ObjectUtils.isNotEmpty(token)){
            return R.success(userVO);
        }

        String tokenKey = RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN +uuid;

        redisUtils.set(tokenKey,userPO,EXPIRATION_TIME);
        token = JWTUtils.generateToken(uuid,userPO.getId());
        redisUtils.set(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userPO.getId(),token,REDIS_LOGIN_STATUS_EXPIRATION_TIME);

        userVO.setAccessToken(token);
        return R.success(userVO);
    }
}
