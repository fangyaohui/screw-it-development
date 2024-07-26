package com.fang.screw.upm.controller;

import com.fang.screw.upm.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import po.UserInfoPO;

/**
 * @FileName RegisterController
 * @Description 用户注册
 * @Author yaoHui
 * @date 2024-07-18
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {


    @RequestMapping("/user")
    public R<Boolean> registerUser(@RequestBody UserInfoPO userInfoPO){
        return R.ok(true);
    }


}