package com.fang.screw.upm.controller;

import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.BlogUserVO;

import java.util.List;

/**
 * @FileName UserController
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;


}
