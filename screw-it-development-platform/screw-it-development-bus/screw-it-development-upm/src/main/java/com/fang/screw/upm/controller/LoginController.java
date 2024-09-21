package com.fang.screw.upm.controller;

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
    public R<UserVO> login(@RequestParam("account") String account,
                                      @RequestParam("password") String password,
                                      @RequestParam(value = "isAdmin", defaultValue = "false") Boolean isAdmin) {
        return loginService.login(account, password, isAdmin);
    }

}
