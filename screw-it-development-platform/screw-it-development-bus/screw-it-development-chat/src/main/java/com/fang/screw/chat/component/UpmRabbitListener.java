package com.fang.screw.chat.component;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.chat.mapper.RelUserFriendMapper;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @FileName UpmRabbitListener
 * @Description
 * @Author yaoHui
 * @date 2024-11-01
 **/
@Component
@AllArgsConstructor
@Slf4j
public class UpmRabbitListener {

    private RelUserFriendMapper relUserFriendMapper;

    /***
    * @Description 用户更改信息后，就会将相关的信息扩散至其相关好友内
    * @param userPO
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/11/1
    */
    @RabbitListener(queues = "updateUserInfoQueue")
    public void updateUserInfoQueueListener(UserVO userVO){
        if (ObjectUtils.isEmpty(userVO)){
            log.info("用户相关好友信息更改出现用户信息为空");
            return ;
        }
        RelUserFriendPO relUserFriendPO = new RelUserFriendPO();
        relUserFriendPO.setUserBName(userVO.getUsername());
        relUserFriendPO.setAvatar(userVO.getAvatar());
        relUserFriendMapper.update(relUserFriendPO, Wrappers.<RelUserFriendPO>lambdaUpdate()
                .eq(RelUserFriendPO::getUserB,userVO.getId()));
    }
}
