package com.fang.screw.chat.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.chat.feign.UserFeign;
import com.fang.screw.chat.mapper.MessageMapper;
import com.fang.screw.chat.mapper.RelUserFriendMapper;
import com.fang.screw.chat.service.ChatService;
import com.fang.screw.communal.Interceptor.CurrentUserInterceptor;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.MessagePO;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.MessageVO;
import com.fang.screw.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.DynamicParameter.NOT_DEL_FLAG;

/**
 * @FileName ChatServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private RelUserFriendMapper relUserFriendMapper;

    private UserFeign userFeign;

    private MessageMapper messageMapper;

    /***
     * @Description 根据用户ID获取该用户的所有好友
     * @param userId
     * @return {@link R< List< UserVO>> }
     * @Author yaoHui
     * @Date 2024/10/8
     */
    @Override
    public R<List<RelUserFriendPO>> getFriendsListById() {

        Integer userId = CurrentUserHolder.getUser().getId();
        List<RelUserFriendPO> userPOList = relUserFriendMapper.selectList(Wrappers.<RelUserFriendPO>lambdaQuery()
                .eq(RelUserFriendPO::getUserA,userId)
                .eq(RelUserFriendPO::getDelFlag,NOT_DEL_FLAG));

        return R.ok(userPOList);
    }

    /***
     * @Description 添加用户好友
     * @param userId
     * @return {@link R< List< RelUserFriendPO>> }
     * @Author yaoHui
     * @Date 2024/10/8
     */
    @Override
    public R<String> addFriend(Integer userId) {

        UserDTO userDTO = userFeign.getUserDTOById(userId).getData();
        if(ObjectUtils.isEmpty(userDTO)){
            return R.failed("查无此人");
        }

        Integer currentUserId = CurrentUserHolder.getUser().getId();
        if(relUserFriendMapper.exists(Wrappers.<RelUserFriendPO>lambdaQuery()
                .eq(RelUserFriendPO::getUserA,currentUserId)
                .eq(RelUserFriendPO::getUserB,userId)
                .eq(RelUserFriendPO::getDelFlag,NOT_DEL_FLAG))){
            return R.ok(null,"已经添加为好友");
        }

        RelUserFriendPO relUserFriendPO = new RelUserFriendPO();
        relUserFriendPO.setUserA(currentUserId);
        relUserFriendPO.setUserB(userId);
        relUserFriendPO.setUserBName(userDTO.getUsername());
        relUserFriendPO.setAvatar(userDTO.getAvatar());
        relUserFriendPO.setDelFlag(NOT_DEL_FLAG);

        if(relUserFriendMapper.exists(Wrappers.<RelUserFriendPO>lambdaQuery()
                .eq(RelUserFriendPO::getUserA,currentUserId)
                .eq(RelUserFriendPO::getUserB,userId))){
            relUserFriendMapper.update(relUserFriendPO,Wrappers.<RelUserFriendPO>lambdaUpdate()
                    .eq(RelUserFriendPO::getUserA,currentUserId)
                    .eq(RelUserFriendPO::getUserB,userId));
            return R.ok(null,"添加好友成功");
        }

        relUserFriendMapper.insert(relUserFriendPO);
        relUserFriendPO.setId(null);
        relUserFriendPO.setUserA(userId);
        relUserFriendPO.setUserB(currentUserId);
        relUserFriendPO.setUserBName(CurrentUserHolder.getUser().getUsername());
        relUserFriendPO.setAvatar(CurrentUserHolder.getUser().getAvatar());
        relUserFriendMapper.insert(relUserFriendPO);

        return R.ok(null,"添加好友成功");
    }

    /***
     * @Description 获取聊天记录
     * @param messageVO
     * @return {@link R< List< MessageVO>> }
     * @Author yaoHui
     * @Date 2024/10/9
     */
    @Override
    public R<List<MessageVO>> getMessage(RelUserFriendPO relUserFriendPO) {
        if(ObjectUtils.isEmpty(relUserFriendPO)){
            return R.failed("请重试");
        }

        List<MessagePO> messagePOS = messageMapper.selectList(Wrappers.<MessagePO>lambdaQuery()
                .eq(MessagePO::getSendUser,relUserFriendPO.getUserA())
                .eq(MessagePO::getReceiveUser,relUserFriendPO.getUserB())
                        .or()
                .eq(MessagePO::getSendUser,relUserFriendPO.getUserB())
                .eq(MessagePO::getReceiveUser,relUserFriendPO.getUserA()));
        if(ObjectUtils.isEmpty(messagePOS)){
            return R.ok(null);
        }

        List<MessageVO> messageVOList = messagePOS.stream().map(MessagePO::transformVO).collect(Collectors.toList());

        return R.ok(messageVOList);
    }


}
