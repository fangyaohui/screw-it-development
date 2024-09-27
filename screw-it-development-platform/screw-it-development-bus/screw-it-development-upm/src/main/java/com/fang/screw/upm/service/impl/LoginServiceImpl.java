package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.communal.constant.RedisDynamicParameter;
import com.fang.screw.communal.utils.*;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.BlogUserVO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.enums.ExceptionEnum;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.mapper.UserMapper;
import com.fang.screw.upm.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.LoginVO;

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

    private UserMapper userMapper;

    /***
    * @Description 登录：可以通过手机号、用户名或者邮箱进行登录
    * @param loginVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    @Override
    public R<LoginVO> signIn(LoginVO loginVO) {
        BlogUserPO userInfoPO = blogUserMapper.selectOne(Wrappers.<BlogUserPO>lambdaQuery()
                .eq(RegexUtils.isMobile(loginVO.getUserName()),BlogUserPO::getPhone,loginVO.getUserName())
                        .or()
                .eq(RegexUtils.isEmailAddress(loginVO.getUserName()),BlogUserPO::getEmail,loginVO.getUserName())
                        .or()
                        .eq(BlogUserPO::getUserName,loginVO.getUserName())
                );

        if(userInfoPO == null){
            return R.failed("登录失败");
        }

        if(!MD5WithSaltUtils.verify(loginVO.getPassword(),userInfoPO.getSalt(),userInfoPO.getPassword())){
            return R.failed("登录失败");
        }

        String uuid = String.valueOf(UUID.randomUUID());

        // 判断该用户是否登录 登陆过就无需再生成Token然后保存到Redis中
        String token = (String) redisUtils.get(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userInfoPO.getId());
        BlogUserVO blogUserVO = userInfoPO.transformToVO();
        LoginVO loginVO1 = new LoginVO();
        loginVO1.setToken(token);
        loginVO1.setBlogUserVO(blogUserVO);
        if(ObjectUtils.isNotEmpty(token)){
            return R.ok(loginVO1);
        }

        String tokenKey = RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN +uuid;

        redisUtils.set(tokenKey,userInfoPO,EXPIRATION_TIME);
        token = JWTUtils.generateToken(uuid,1);
        redisUtils.set(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userInfoPO.getId(),token,REDIS_LOGIN_STATUS_EXPIRATION_TIME);

        return R.ok(loginVO1);
    }

    /***
    * @Description 登录逻辑
    * @param account
    * @param password
    * @param isAdmin
    * @return {@link R< UserVO> }
    * @Author yaoHui
    * @Date 2024/9/20
    */
    @Override
    public R<UserVO> login(String account, String password, Boolean isAdmin,String privateKey) {

        UserPO userPO = userMapper.selectOne(Wrappers.<UserPO>lambdaQuery()
                .eq(RegexUtils.isMobile(account),UserPO::getPhoneNumber,account)
                .or()
                .eq(RegexUtils.isEmailAddress(account),UserPO::getEmail,account)
                .or()
                .eq(UserPO::getUsername,account));


        if(userPO == null){
            return R.failed("登录失败");
        }

        if(!MD5WithSaltUtils.verify(password,userPO.getPassword())){
            return R.failed("登录失败");
        }

        if(!userPO.getUserStatus()){
            return R.fail("账号被冻结");
        }

        String uuid = String.valueOf(UUID.randomUUID());

        // 判断该用户是否登录 登陆过就无需再生成Token然后保存到Redis中
        String token = (String) redisUtils.get(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userPO.getId());

        UserVO userVO = userPO.transformVO();
        userVO.setPassword(null);
        userVO.setAccessToken(token);

        if(ObjectUtils.isNotEmpty(token)){
            return R.success(userVO);
        }

        String tokenKey = RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN +uuid;

        redisUtils.set(tokenKey,userPO,EXPIRATION_TIME);
        token = JWTUtils.generateToken(uuid,userPO.getId());
        redisUtils.set(RedisDynamicParameter.REDIS_USER_LOGIN_STATUS + userPO.getId(),token,REDIS_LOGIN_STATUS_EXPIRATION_TIME);
        redisUtils.set(RedisDynamicParameter.REDIS_CRYPT_AES_PRIVATE + token,privateKey);

        userVO.setAccessToken(token);
        return R.success(userVO);
    }

    /**
     * @Description 用户进行登录之前访问该API获得后端RSA的公钥
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/9/27
     */
    @Override
    public R<String> getRsaPublicKey() {
        return R.ok(RsaUtils.getPublicKey());
    }

    /***
     * @Description 获取客户端生成的AES密钥，需要使用RSA公钥解密获得，并保存到Redis中进行维护
     * @param privateKey
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/9/27
     */
    @Override
    public R<String> pushAesPrivateKey(String privateKey) {

        if(ObjectUtils.isEmpty(privateKey)){
            return R.fail("客户端验证密钥存在问题！请稍后再试");
        }

        try{
            String aesPrivateKey = RsaUtils.decryptByPrivateKey(privateKey,String.class);
        }catch (Exception e){
            return R.fail("AES密钥解密失败，请稍后再试");
        }

        return null;
    }
}
