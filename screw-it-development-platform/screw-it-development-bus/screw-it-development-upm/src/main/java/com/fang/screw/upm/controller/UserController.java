package com.fang.screw.upm.controller;

import com.fang.screw.communal.annotation.CacheRemove;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.vo.FamilyVO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    * @Description 更新用户的相关信息
    * @param userVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/10/23
    */
    @PostMapping("/updateUserInfo")
    @CacheRemove(value = "user:login:status",key = "#0.id")
    public R<UserVO> updateUserInfo(@RequestBody UserVO userVO){
        return userService.updateUserInfo(userVO);
    }

    /***
    * @Description 查询当前用户的Family相关信息
    * @return {@link R< FamilyVO> }
    * @Author yaoHui
    * @Date 2024/11/2
    */
    @PostMapping("/getUserFamily")
    public R<FamilyVO> getUserFamily(){
        return userService.getUserFamily();
    }

    /***
    * @Description 保存用户的相关Family信息——新建
    * @param familyVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/11/2
    */
    @PostMapping("/saveAndUpdateFamily")
    public R<String> saveAndUpdateFamily(@RequestBody FamilyVO familyVO){
        return userService.saveAndUpdateFamily(familyVO);
    }


}
