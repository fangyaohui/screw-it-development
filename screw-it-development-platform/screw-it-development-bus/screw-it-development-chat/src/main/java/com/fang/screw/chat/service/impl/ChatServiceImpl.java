package com.fang.screw.chat.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.chat.mapper.RelUserFriendMapper;
import com.fang.screw.chat.service.ChatService;
import com.fang.screw.communal.Interceptor.CurrentUserInterceptor;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        return R.ok(null,"添加好友成功");
    }


}
