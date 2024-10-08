package com.fang.screw.chat.controller;

import com.fang.screw.chat.service.ChatService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName FriendsController
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@RestController
//@RequestMapping("/friend")
@AllArgsConstructor
@Slf4j
public class FriendsController {

    private ChatService chatService;

    /***
    * @Description 根据用户ID获取该用户的所有好友
    * @param userId
    * @return {@link R< List< UserVO>> }
    * @Author yaoHui
    * @Date 2024/10/8
    */
    @GetMapping("/getFriendsListById")
    public R<List<RelUserFriendPO>> getFriendsListById(){
        return chatService.getFriendsListById();
    }

    /***
    * @Description 添加用户好友
    * @param userId
    * @return {@link R< List< RelUserFriendPO>> }
    * @Author yaoHui
    * @Date 2024/10/8
    */
    @PostMapping("/addFriend")
    public R<String> addFriend(@RequestParam("userId") Integer userId){
        return chatService.addFriend(userId);
    }

}
