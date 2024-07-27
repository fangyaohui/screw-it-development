package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.utils.*;
import com.fang.screw.upm.enums.ExceptionEnum;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.service.LoginService;
import com.fang.screw.upm.config.DynamicConstant;
import dto.BlogUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import po.BlogUserPO;
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

    private BlogUserMapper blogUserMapper;

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
                );
        if(ObjectUtils.isEmpty(userInfoPO)){
            userInfoPO = blogUserMapper.selectOne(Wrappers.<BlogUserPO>lambdaQuery()
                    .eq(BlogUserPO::getUserName,loginVO.getUserName()));
        }

        if(ObjectUtils.isEmpty(userInfoPO)){
            return ExceptionEnum.SYSOP_USERNAME_ERROR.getBusinessException();
        }

        if(!MD5WithSaltUtils.verify(loginVO.getPassword(),userInfoPO.getSalt(),userInfoPO.getPassword())){
            return ExceptionEnum.SYSOP_USERNAME_ERROR.getBusinessException();
        }

        String uuid = String.valueOf(UUID.randomUUID());
        BlogUserDTO blogUserDTO = userInfoPO.transformToDTO();

        RedisUtils.set(uuid,blogUserDTO);
        String token = JWTUtils.generateToken(uuid,1L);

        return R.ok(token);
    }
}