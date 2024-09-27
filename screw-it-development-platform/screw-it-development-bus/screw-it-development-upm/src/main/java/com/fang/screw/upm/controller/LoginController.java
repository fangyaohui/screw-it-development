package com.fang.screw.upm.controller;

import com.fang.screw.communal.annotation.Decrypt;
import com.fang.screw.communal.annotation.Encrypt;
import com.fang.screw.communal.utils.AesUtils;
import com.fang.screw.domain.vo.BlogUserVO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.service.LoginService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fang.screw.domain.vo.LoginVO;

/**
 * @FileName LoginController
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
@RestController
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @RequestMapping("/login/signIn")
    public R<LoginVO> signIn(@RequestBody LoginVO loginVO){
        return loginService.signIn(loginVO);
    }

    /**
     * 用户名、邮箱、手机号/密码登录
     */
    @PostMapping("/login")
    public R<UserVO> login(@RequestBody LoginVO loginVO) {
        return loginService.login(loginVO.getAccount(),loginVO.getPassword(),false,loginVO.getPrivateKey());
    }

    /**
    * @Description 用户进行登录之前访问该API获得后端RSA的公钥
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/9/27
    */
    @GetMapping("/login/getRsaPublicKey")
    public R<String> getRsaPublicKey(){
        return loginService.getRsaPublicKey();
    }

    @GetMapping("/login/getAesPublicKey")
    public R<String> getAesPublicKey(){
        return R.ok(AesUtils.getKey());
//        return loginService.getRsaPublicKey();
    }

    /***
    * @Description 获取客户端生成的AES密钥，需要使用RSA公钥解密获得，并保存到Redis中进行维护
    * @param privateKey
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/9/27
    */
     @GetMapping("/login/pushAesPrivateKey")
    public R<String> pushAesPrivateKey(String privateKey){
        return loginService.pushAesPrivateKey(privateKey);
    }

}
