package com.fang.screw.upm.controller;

import com.fang.screw.communal.utils.BusinessException;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.service.RegisterService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fang.screw.domain.vo.RegisterUserInfoVO;

/**
 * @FileName RegisterController
 * @Description 用户注册
 * @Author yaoHui
 * @date 2024-07-18
 **/
@RestController
@AllArgsConstructor
//@RequestMapping("/register")
public class RegisterController {

    private RegisterService registerService;

    /***
    * @Description 注册用户-older
    * @param registerUserInfoVO
    * @return {@link R< Boolean> }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    @RequestMapping("/register/user")
    public R<String> registerUser(@RequestBody RegisterUserInfoVO registerUserInfoVO) throws BusinessException {
        return registerService.registerUser(registerUserInfoVO);
    }

    @PostMapping("/register")
    public R<UserVO> registerUser(@Validated @RequestBody UserVO user){
        return registerService.registerUser(user);
    }



}
