package com.fang.screw.chat.controller;

import com.fang.screw.chat.service.ChatService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.domain.po.RelUserFriendPO;
import com.fang.screw.domain.vo.MessageVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @FileName MessageController
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/message")
public class MessageController {

    private final RedisUtils redis;

    private ChatService chatService;

    /***
    * @Description 获取聊天记录
    * @param messageVO
    * @return {@link R< List< MessageVO>> }
    * @Author yaoHui
    * @Date 2024/10/9
    */
    @PostMapping("/getMessage")
    public R<List<MessageVO>> getMessage(@RequestBody RelUserFriendPO relUserFriendPO) {
        return chatService.getMessage(relUserFriendPO);
    }
}
