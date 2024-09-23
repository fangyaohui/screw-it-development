package com.fang.screw.upm.controller;

import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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

    /***
    * @Description 根据用户ID查询该用户对应的具体用户信息
    * @param userId
    * @return {@link R< UserDTO> }
    * @Author yaoHui
    * @Date 2024/9/23
    */
    @GetMapping("/getUserDTOById/{userId}")
    public R<UserDTO> getUserDTOById(@PathVariable String userId){
        return userService.getUserDTOById(userId);
    }


}
