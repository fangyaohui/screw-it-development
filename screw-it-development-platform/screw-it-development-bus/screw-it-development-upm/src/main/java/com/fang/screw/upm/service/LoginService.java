package com.fang.screw.upm.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.BlogUserVO;
import com.fang.screw.domain.vo.LoginVO;
import com.fang.screw.domain.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @FileName LoginService
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
public interface LoginService {

    R<LoginVO> signIn(LoginVO loginVO);

    R<UserVO> login(String account, String password, Boolean isAdmin,String privateKey);

    /**
     * @Description 用户进行登录之前访问该API获得后端RSA的公钥
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/9/27
     */
    R<String> getRsaPublicKey();

    /***
     * @Description 获取客户端生成的AES密钥，需要使用RSA公钥解密获得，并保存到Redis中进行维护
     * @param privateKey
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/9/27
     */
    R<String> pushAesPrivateKey(String privateKey);
}
