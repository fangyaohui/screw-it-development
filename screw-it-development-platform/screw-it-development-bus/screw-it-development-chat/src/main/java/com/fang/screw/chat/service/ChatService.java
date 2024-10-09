package com.fang.screw.chat.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.vo.MessageVO;
import com.fang.screw.domain.vo.UserVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @FileName ChatService
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
public interface ChatService {

    /***
     * @Description 根据用户ID获取该用户的所有好友
     * @param userId
     * @return {@link R< List< UserVO>> }
     * @Author yaoHui
     * @Date 2024/10/8
     */
    R<List<RelUserFriendPO>> getFriendsListById();

    /***
     * @Description 添加用户好友
     * @param userId
     * @return {@link R< List< RelUserFriendPO>> }
     * @Author yaoHui
     * @Date 2024/10/8
     */
    R<String> addFriend(Integer userId);

    /***
     * @Description 获取聊天记录
     * @param messageVO
     * @return {@link R< List< MessageVO>> }
     * @Author yaoHui
     * @Date 2024/10/9
     */
    R<List<MessageVO>> getMessage(RelUserFriendPO relUserFriendPO);
}
