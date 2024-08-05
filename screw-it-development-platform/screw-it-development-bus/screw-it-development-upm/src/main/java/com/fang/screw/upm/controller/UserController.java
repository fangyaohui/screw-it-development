package com.fang.screw.upm.controller;

import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import po.BlogUserPO;
import po.UserInfoPO;
import vo.BlogUserVO;

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

    private UserDubboService userDubboService;

    @GetMapping("/getAllUserInfoList")
    public R<String> getAllUserInfoList(){
        List<BlogUserPO> userInfoPOList = userDubboService.getAllUserInfoList();
        if(userInfoPOList.isEmpty()){
            return null;
        }
        return R.ok(userInfoPOList.toString());
    }

    @GetMapping("/test")
    public R<String> getTest(){
        log.info("test");
        return R.ok("test");
    }

    /**
    * @Description 根据用户ID查询用户的具体信息
    * @param userId
    * @return {@link R< BlogUserVO> }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    @RequestMapping("/getUserInfoByUserId")
    public R<BlogUserVO> getUserInfoByUserId(@RequestParam("userId") Long userId){
        return userDubboService.getUserInfoByUserId(userId);
    }

}