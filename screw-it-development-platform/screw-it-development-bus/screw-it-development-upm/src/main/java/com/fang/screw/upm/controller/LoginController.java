package com.fang.screw.upm.controller;

import com.fang.screw.domain.vo.BlogUserVO;
import com.fang.screw.upm.service.LoginService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fang.screw.domain.vo.LoginVO;

/**
 * @FileName LoginController
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @RequestMapping("/signIn")
    public R<LoginVO> signIn(@RequestBody LoginVO loginVO){
        return loginService.signIn(loginVO);
    }

}
