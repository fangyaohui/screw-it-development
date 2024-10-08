package com.fang.screw.chat.controller;

import com.fang.screw.communal.utils.RedisUtils;
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

    @PostMapping("/getMessage")
    public List<MessageVO> getMessage(@RequestBody MessageVO messageVO) {

        List<MessageVO> messageVOList = new ArrayList<>();

        messageVO.setMessage("test");
        messageVOList.add(messageVO);
        return messageVOList;

//        String key = LongStream.of(messageVO.getSendUser(), messageVO.getReceiveUser())
//                .sorted()
//                .mapToObj(String::valueOf)
//                .collect(Collectors.joining("-"));
//
//        return (List<Object>) redis.get(key);
    }
}
