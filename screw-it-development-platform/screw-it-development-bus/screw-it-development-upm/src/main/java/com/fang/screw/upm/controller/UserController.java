package com.fang.screw.upm.controller;

import com.fang.screw.communal.annotation.CacheRemove;
import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.vo.UserVO;
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

    /***
    * @Description 根据keyword查询用户名、电子邮件、电话等相关的用户列表
    * @param keyword
    * @return {@link R< List< UserDTO>> }
    * @Author yaoHui
    * @Date 2024/10/9
    */
    @GetMapping("/getUserByUserNameOrEmail/{keyword}")
    public R<List<UserVO>> getUserByUserNameOrEmail(@PathVariable("keyword") String keyword){
        return userService.getUserByUserNameOrEmail(keyword);
    }

    /***
    * @Description 更新用户的头像
    * @param userVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/10/23
    */
    // TODO 更新用户头像或者相关信息之后 Redis中的数据没有更新 导致用户评论之后的头像还是Redis中的老数据 应为comment是从Redis获取用户头像的
    @PostMapping("/updateUserAvatarInfo")
    @CacheRemove(value = "page:article")
    public R<UserVO> updateUserAvatarInfo(@RequestBody UserVO userVO){
        return userService.updateUserAvatarInfo(userVO);
    }


}
