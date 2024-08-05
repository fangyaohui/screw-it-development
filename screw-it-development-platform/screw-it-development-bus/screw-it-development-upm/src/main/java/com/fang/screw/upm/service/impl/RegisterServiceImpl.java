package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.utils.MD5WithSaltUtils;
import com.fang.screw.communal.utils.RegexUtils;
import com.fang.screw.upm.enums.ExceptionEnum;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.service.RegisterService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.RegisterUserInfoVO;

import java.time.LocalDateTime;
import java.util.UUID;

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
}