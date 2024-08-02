package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.constant.RedisDynamicParameter;
import com.fang.screw.communal.utils.*;
import com.fang.screw.upm.enums.ExceptionEnum;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import po.BlogUserPO;
import vo.LoginVO;

import java.util.UUID;

import static com.fang.screw.communal.constant.DynamicParameter.EXPIRATION_TIME;
import static com.fang.screw.communal.constant.DynamicParameter.REDIS_LOGIN_STATUS_EXPIRATION_TIME;

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

    private BlogUserMapper blogUserMapper;

    private RedisUtils redisUtils;

    /***
    * @Description 登录：可以通过手机号、用户名或者邮箱进行登录
    * @param loginVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    @Override
    public R<String> signIn(LoginVO loginVO) {
        BlogUserPO userInfoPO = blogUserMapper.selectOne(Wrappers.<BlogUserPO>lambdaQuery()
                .eq(RegexUtils.isMobile(loginVO.getUserName()),BlogUserPO::getPhone,loginVO.getUserName())
                        .or()
                .eq(RegexUtils.isEmailAddress(loginVO.getUserName()),BlogUserPO::getEmail,loginVO.getUserName())
                        .or()
                        .eq(BlogUserPO::getUserName,loginVO.getUserName())
                );

        if(userInfoPO == null){
            return ExceptionEnum.SYSOP_USERNAME_ERROR.getBusinessException();
        }

        if(!MD5WithSaltUtils.verify(loginVO.getPassword(),userInfoPO.getSalt(),userInfoPO.getPassword())){
            return ExceptionEnum.SYSOP_USERNAME_ERROR.getBusinessException();
        }

        String uuid = String.valueOf(UUID.randomUUID());

        // 判断该用户是否登录 登陆过就无需再生成Token然后保存到Redis中
        String token = (String) redisUtils.get(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userInfoPO.getId());
        if(ObjectUtils.isNotEmpty(token)){
            return R.ok(token);
        }

        String tokenKey = RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN +uuid;

        redisUtils.set(tokenKey,userInfoPO,EXPIRATION_TIME);
        token = JWTUtils.generateToken(uuid,1L);
        redisUtils.set(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userInfoPO.getId(),token,REDIS_LOGIN_STATUS_EXPIRATION_TIME);

        return R.ok(token);
    }
}
